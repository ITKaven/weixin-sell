package com.kaven.weixinsell.service.impl;

import com.kaven.weixinsell.converter.OrderMasterToOrderDTOConverter;
import com.kaven.weixinsell.dataobject.OrderDetail;
import com.kaven.weixinsell.dataobject.OrderMaster;
import com.kaven.weixinsell.dataobject.ProductInfo;
import com.kaven.weixinsell.dto.CartDTO;
import com.kaven.weixinsell.dto.OrderDTO;
import com.kaven.weixinsell.enums.OrderStatusEnum;
import com.kaven.weixinsell.enums.PayStatusEnum;
import com.kaven.weixinsell.enums.ResultEnum;
import com.kaven.weixinsell.exception.SellException;
import com.kaven.weixinsell.repository.OrderDetailRepository;
import com.kaven.weixinsell.repository.OrderMasterRepository;
import com.kaven.weixinsell.service.OrderService;
import com.kaven.weixinsell.service.ProductInfoService;
import com.kaven.weixinsell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductInfoService productInfoService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        // order id
        String orderId = KeyUtil.getUniqueKey();

        BigDecimal orderAmount = new BigDecimal(0);

//        List<CartDTO> cartDTOList = new ArrayList<>();

        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null) throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);

            BeanUtils.copyProperties(productInfo , orderDetail);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);

            orderAmount = orderAmount.add(orderDetail.getProductPrice().
                    multiply(new BigDecimal(orderDetail.getProductQuantity())));

            orderDetailRepository.save(orderDetail);

//            CartDTO cartDTO = new CartDTO();
//            BeanUtils.copyProperties(orderDetail,cartDTO);

//            cartDTOList.add(cartDTO);
        }

        OrderMaster orderMaster = new OrderMaster();

        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmount);

        // orderDTO 中属性为 null 也会进行 copy，只要属性名相同
        BeanUtils.copyProperties(orderDTO , orderMaster);
        orderMasterRepository.save(orderMaster);

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().
                map(e-> new CartDTO(e.getProductQuantity(),e.getProductId())).
                collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster == null)
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList))
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String orderOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage = orderMasterRepository.
                findByBuyerOpenid(orderOpenid ,pageable);
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter.
                converter(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,
                orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        // 判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        OrderMaster updateResult = orderMasterRepository.save(orderMaster);

        if(updateResult == null){
            log.error("【订单取消】 订单状态更新失败 orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // 返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【订单取消】 订单商品详情为空 orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().
                map(e-> new CartDTO(e.getProductQuantity(),e.getProductId())).
                collect(Collectors.toList());

        productInfoService.increaseStock(cartDTOList);

        // 如果已支付,需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finished(OrderDTO orderDTO) {
        // 判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】 订单状态不正确 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result == null ){
            log.error("【完结订单】 订单状态更新失败 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        // 判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】 订单状态不正确 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】 订单支付状态错误 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        // 修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result == null){
            log.error("【支付订单】 订单状态更新失败 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }
}

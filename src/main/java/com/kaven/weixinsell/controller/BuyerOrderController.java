package com.kaven.weixinsell.controller;

import com.kaven.weixinsell.converter.OrderFormToOrderDTOConverter;
import com.kaven.weixinsell.dataobject.OrderDetail;
import com.kaven.weixinsell.dataobject.OrderMaster;
import com.kaven.weixinsell.dto.OrderDTO;
import com.kaven.weixinsell.enums.ResultEnum;
import com.kaven.weixinsell.exception.SellException;
import com.kaven.weixinsell.from.OrderForm;
import com.kaven.weixinsell.service.BuyerService;
import com.kaven.weixinsell.service.OrderService;
import com.kaven.weixinsell.utils.ResultVueObjectUtil;
import com.kaven.weixinsell.vueObject.ResultVueObject;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    // 创建订单
    @PostMapping("/create")
    public ResultVueObject<Map<String,String>> create(@Valid OrderForm orderForm ,
                                                      BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】 订单参数不正确 orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode() ,
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderFormToOrderDTOConverter.converter(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO result = orderService.create(orderDTO);
        Map<String , String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());

        return ResultVueObjectUtil.success(map);
    }

    // 订单列表
    @GetMapping("/list")
    public ResultVueObject<List<OrderDTO>> list(@RequestParam("openid") String openid ,
                                                @RequestParam(value ="page" ,defaultValue = "0") Integer page ,
                                                @RequestParam(value ="size" ,defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【订单查询】 openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = new PageRequest(page,size);

        Page<OrderDTO> orderDTOPage = orderService.findList(openid,request);

        return ResultVueObjectUtil.success(orderDTOPage.getContent());
    }

    // 订单详情
    @GetMapping("/detail")
    public ResultVueObject<OrderDTO> details(@RequestParam("orderId") String orderId ,
                                             @RequestParam("openid") String openid){
        // TODO 需要验证用户信息
        buyerService.findOrderOne(orderId , openid);
        return ResultVueObjectUtil.success(orderService.findOne(orderId));
    }

    // 取消订单
    @PostMapping("/cancel")
    public ResultVueObject cancel(@RequestParam("orderId") String orderId ,
                                            @RequestParam("openid") String openid){
        // TODO 需要验证用户信息
        buyerService.calcelOrderOne(orderId , openid);
        return ResultVueObjectUtil.success();
    }
}

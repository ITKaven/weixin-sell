package com.kaven.weixinsell.converter;

import com.kaven.weixinsell.dataobject.OrderMaster;
import com.kaven.weixinsell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderMasterToOrderDTOConverter {

    public static Function<OrderMaster , OrderDTO> function = orderMaster -> {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    };

    public static List<OrderDTO> converter(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(function).collect(Collectors.toList());
    }
}

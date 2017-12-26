package org.elvis.wang.service.impl;

import org.elvis.wang.dal.dao.OrderItemMapper;
import org.elvis.wang.model.OrderItem;
import org.elvis.wang.service.OrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhiqun.wang
 * @since JDK 1.7
 */
@Service("orderItemService")
public class OrderItemServiceImpl implements OrderItemService{
    @Resource
    public OrderItemMapper orderItemMapper;

    public List<OrderItem> findList(OrderItem orderItem) throws Exception {
        return orderItemMapper.findList(orderItem);
    }
}

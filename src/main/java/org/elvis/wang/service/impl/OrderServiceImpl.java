package org.elvis.wang.service.impl;

import org.elvis.wang.dal.dao.OrderMapper;
import org.elvis.wang.model.Order;
import org.elvis.wang.mybatis.page.model.Page;
import org.elvis.wang.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhiqun.wang on 2017/11/1.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    public OrderMapper orderMapper;

    public void insert(Order order) throws Exception {
        orderMapper.insert(order);
    }

    public int update(Order order) throws Exception {
        return orderMapper.update(order);
    }

    public Order findById(int orderId) throws Exception {
        return orderMapper.findById(orderId);
    }

    public List<Order> findList(Order order) throws Exception {
        return orderMapper.findList(order);
    }

    public int batchInsert(List<Order> orderList) throws Exception {
        return orderMapper.batchInsert(orderList);
    }

    public int batchUpdate(List<Order> orderList) throws Exception {
        return orderMapper.batchUpdate(orderList);
    }

    public List<Order> findListByPage(Page<Order> page, Order order) throws Exception {
        return orderMapper.findListByPage(page,order);
    }
}

package org.elvis.wang.dal.dao;

import org.elvis.wang.model.OrderItem;

import java.util.List;

/**
 * create by elvis.wang 2017/11/15
 */
public interface OrderItemMapper {

    public void insert(OrderItem orderItem);

    public int update(OrderItem orderItem);

    public OrderItem findById(int orderItemId);

    public List<OrderItem> findList(OrderItem order);

    public int batchInsert(List<OrderItem> orderList);

    public int batchUpdate(List<OrderItem> orderList);
}

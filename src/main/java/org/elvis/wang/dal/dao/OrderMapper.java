package org.elvis.wang.dal.dao;

import org.elvis.wang.model.Order;
import org.elvis.wang.mybatis.page.model.Page;

import java.util.List;

/**
 * create by elvis.wang 2017/11/15
 */
public interface OrderMapper {

    public void insert(Order order);

    public int update(Order order);

    public Order findById(int orderId);

    public List<Order> findList(Order order);

    public List<Order> findListByPage(Page<Order> page, Order order);

    public int batchInsert(List<Order> orderList);

    public int batchUpdate(List<Order> orderList);

}

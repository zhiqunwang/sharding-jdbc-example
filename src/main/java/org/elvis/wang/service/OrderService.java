package org.elvis.wang.service;

import org.elvis.wang.model.Order;
import org.elvis.wang.mybatis.page.model.Page;

import java.util.List;

/**
 * Created by zhiqun.wang on 2017/11/1.
 */
public interface OrderService {

    public void insert(Order order) throws Exception;

    public int update(Order order) throws Exception;

    public Order findById(int orderId) throws Exception;

    public List<Order> findList(Order order) throws Exception;

    public int batchInsert(List<Order> orderList) throws Exception;

    public int batchUpdate(List<Order> orderList) throws Exception;

    public List<Order> findListByPage(Page<Order> page, Order order) throws Exception;
}

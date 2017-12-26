package org.elvis.wang.service;


import org.elvis.wang.model.OrderItem;

import java.util.List;

/**
 * Created by zhiqun.wang on 2017/11/1.
 */
public interface OrderItemService {

    public List<OrderItem> findList(OrderItem orderItem) throws Exception;
}

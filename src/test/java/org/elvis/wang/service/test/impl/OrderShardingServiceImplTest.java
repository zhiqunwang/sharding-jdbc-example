package org.elvis.wang.service.test.impl;

import com.dangdang.ddframe.rdb.sharding.api.HintManager;
import org.elvis.wang.model.Order;
import org.elvis.wang.model.OrderItem;
import org.elvis.wang.mybatis.page.model.Page;
import org.elvis.wang.service.OrderItemService;
import org.elvis.wang.service.OrderService;
import org.elvis.wang.service.test.AbstractShardingServiceTestSupport;
import org.elvis.wang.utils.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Created by zhiqun.wang on 2017/11/1.
 *
 * 本例分表规则是 多分片键算法类型即 orderId 和 create_time 进行表路由这样就支持历史数据无需迁移
 *
 *
 */
public class OrderShardingServiceImplTest extends AbstractShardingServiceTestSupport {
    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "orderItemService")
    private  OrderItemService orderItemService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    /**
     * 1.插入数据,测试根据orderId%3 分片规则进行路由
     * @throws Exception
     */
    @Test
    public void testInsert() throws Exception {
        for(int i = 100000;i<=100010;i++){
            orderService.insert(new Order(i,800001,new Date(),new Date()));
        }
    }
    /**
     * 2. 按照orderId进行路由
     *
     */
    @Test
    public void testfindById() throws Exception {
        Order order = orderService.findById(100003);
        System.out.print("======="+order==null?null:order.toString());
    }

    /**
     *
     * 3. 按照orderId,create_time进行路由
     */
    @Test
    public void testfindList() throws Exception{
        Order order = new Order();
        order.setOrderId(100003);
        order.setStartTime(DateUtils.convertYYYYMMDDToDate("2017-11-05"));
        order.setEndTime(DateUtils.convertYYYYMMDDToDate("2017-11-19"));
        List<Order> list =  orderService.findList(order);
        System.out.print("========"+list);
    }

    /**
     *
     * 4.按照orderId,create_time进行路由 强制路由主库进行查询
     */
    @Test
    public void testfindListFromMaster() throws Exception{
        Order order = new Order();
        order.setOrderId(100003);
        order.setStartTime(DateUtils.convertYYYYMMDDToDate("2017-11-05"));
        order.setEndTime(DateUtils.convertYYYYMMDDToDate("2017-11-19"));

        HintManager hintManager = HintManager.getInstance();
        hintManager.setMasterRouteOnly();
        List<Order> list =  orderService.findList(order);
        System.out.print("========"+list);
    }

    /**
     * 5.按照orderId,create_time进行路由 强制路由主库进行查询   分页查询
     */
    @Test
    public void testfindByPage() throws Exception{
        Order order = new Order();
        order.setOrderId(100003);
        order.setStartTime(DateUtils.convertYYYYMMDDToDate("2017-11-05"));
        order.setEndTime(DateUtils.convertYYYYMMDDToDate("2017-11-19"));

        HintManager hintManager = HintManager.getInstance();
        hintManager.setMasterRouteOnly();
        Page<Order> page = new Page<Order>(5,1);
        List<Order> list =  orderService.findListByPage(page,order);
        System.out.print("========"+list.size());
    }

    /**
     * 测试对or的支持   sharding-jdbc 明确不支持OR
     * SQLParsingUnsupportedException: Not supported token 'OR'.
     */
    @Test
    public void testOr() throws Exception{
        Order order = new Order();
        order.setOrderId(100003);
        order.setUserId(800002);
        HintManager hintManager = HintManager.getInstance();
        hintManager.setMasterRouteOnly();
        List<Order> list =  orderService.findList(order);
    }

    /**
     * 测试  其他表对or的支持   不支持
     */
    @Test
    public void testOtherQuery() throws Exception{
        OrderItem item = new OrderItem();
        item.setItemId(100001);
        item.setUserId(100001);
        List<OrderItem> list = orderItemService.findList(item);
        System.out.print("========"+list);
    }




} 

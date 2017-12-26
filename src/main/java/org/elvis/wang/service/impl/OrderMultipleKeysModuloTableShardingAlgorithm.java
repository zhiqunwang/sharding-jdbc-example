package org.elvis.wang.service.impl;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.MultipleKeysTableShardingAlgorithm;
import org.elvis.wang.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * zhiqun.wang  2017/11/13
 *
 * account 按照时间范围及主键ID进行查询
 *
 * 需求： t_order 历史表
 *        t_order_${0..2} 分片表
 *
 * 目标时间2017-11-13 之前的数据(分表之前的历史数据)查询路由至order 表,
 * 目标时间2017-11-13 之后的数据（分表之后路由）根据aid取余进行路由
 */
public final class OrderMultipleKeysModuloTableShardingAlgorithm implements MultipleKeysTableShardingAlgorithm {

    public Collection<String> doSharding(final Collection<String> availableTargetNames, final Collection<ShardingValue<?>> shardingValues) {
        Set<Integer> aIdValueSet = getShardingValue4Integer(shardingValues, "order_id");
        Set<Date> createTimeValueSet = getShardingValue4Date(shardingValues, "create_time");

        List<String> result = new ArrayList<String>();
        Date time = DateUtils.convertYYYYMMDDToDate("2017-11-10");
        //先判断create_time
        for(Date date:createTimeValueSet){
            if(date.before(time)){
                result.add("t_order");
            }
        }

        for (Integer value : aIdValueSet) {
            for (String tableName : availableTargetNames) {
                if (tableName.endsWith(value % 3 +"")) {
                    result.add(tableName);
                }
            }

        }
        return result;
    }

    private Set<Integer> getShardingValue4Integer(final Collection<ShardingValue<?>> shardingValues, final String shardingKey) {
        Set<Integer> valueSet = new HashSet<Integer>();
        ShardingValue<Integer> shardingValue = null;
        for (ShardingValue<?> each : shardingValues) {
            if (each.getColumnName().toLowerCase().equals(shardingKey)) {
                shardingValue = (ShardingValue<Integer>) each;
                break;
            }
        }
        if (null == shardingValue) {
            return valueSet;
        }
        switch (shardingValue.getType()) {
            case SINGLE:
                valueSet.add(shardingValue.getValue());
                break;
            case LIST:
                valueSet.addAll(shardingValue.getValues());
                break;
            case RANGE:
                for (Integer i = shardingValue.getValueRange().lowerEndpoint(); i <= shardingValue.getValueRange().upperEndpoint(); i++) {
                    valueSet.add(i);
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return valueSet;
    }

    private Set<Date> getShardingValue4Date(final Collection<ShardingValue<?>> shardingValues, final String shardingKey) {
        Set<Date> valueSet = new HashSet<Date>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ShardingValue<Date> shardingValue = null;
        for (ShardingValue<?> each : shardingValues) {
            if (each.getColumnName().toLowerCase().equals(shardingKey)) {
                shardingValue =(ShardingValue<Date>) each;
                break;
            }
        }
        if (null == shardingValue) {
            return valueSet;
        }
        switch (shardingValue.getType()) {
            case SINGLE:
                valueSet.add(shardingValue.getValue());
                break;
            case LIST:
                valueSet.addAll(shardingValue.getValues());
                break;
            case RANGE:
                for (Date i = shardingValue.getValueRange().lowerEndpoint(); i.before(shardingValue.getValueRange().upperEndpoint()) ;i = DateUtils.getDateAfter(i,1)) {
                    valueSet.add(i);
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return valueSet;
    }
}

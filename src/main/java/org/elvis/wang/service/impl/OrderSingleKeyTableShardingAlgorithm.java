package org.elvis.wang.service.impl;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by zhiqun.wang on 2017/11/2.
 */
public class OrderSingleKeyTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {
    /**
     * sql 中 = 操作时，table的映射
     */

    /**
     *  select * from t_order from t_order where order_id = 11
     *          └── SELECT *  FROM t_order_2 WHERE order_id = 11
     *  select * from t_order from t_order where order_id = 44
     *          └── SELECT *  FROM t_order_1 WHERE order_id = 44
     */
    public String doEqualSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
        for (String each : tableNames) {
            if (each.endsWith(shardingValue.getValue() % 3 + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * sql 中 in 操作时，table的映射
     */
    /**
     *  select * from t_order from t_order where order_id in (11,44)
     *          ├── SELECT *  FROM t_order_1 WHERE order_id IN (11,44)
     *          └── SELECT *  FROM t_order_2 WHERE order_id IN (11,44)
     *  select * from t_order from t_order where order_id in (11,13,16)
     *          └── SELECT *  FROM t_order_1 WHERE order_id IN (10,13,16)
     *  select * from t_order from t_order where order_id in (9,27,36)
     *          └──SELECT *  FROM t_order_0 WHERE order_id IN (9,27,36)
     */
    public Collection<String> doInSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(tableNames.size());
        for (Integer value : shardingValue.getValues()) {
            for (String tableName : tableNames) {
                if (tableName.endsWith(value % 3 + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    /**
     * sql 中 between 操作时，table的映射
     */
    /**
     *  select * from t_order from t_order where order_id between 10 and 20
     *          ├── SELECT *  FROM t_order_0 WHERE order_id BETWEEN 10 AND 20
     *          └── SELECT *  FROM t_order_1 WHERE order_id BETWEEN 10 AND 20
     */
    public Collection<String> doBetweenSharding(Collection<String> tableNames,
                                                ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(tableNames.size());
        Range<Integer> range = (Range<Integer>) shardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : tableNames) {
                if (each.endsWith(i % 3 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}

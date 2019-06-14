package org.xiaofan.sql.mybatis.dao;

import org.springframework.stereotype.Service;
import org.xiaofan.sql.spark.auto.TableModel.SparkApp;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */

@Service("sparkAppMapper")
public abstract  class SparkAppMapper {
    public abstract int insert(SparkApp paramSPARK_APP);

    public abstract int insertSelective(SparkApp paramSPARK_APP);

    public abstract SparkApp selectByAppId(Long paramLong);
}

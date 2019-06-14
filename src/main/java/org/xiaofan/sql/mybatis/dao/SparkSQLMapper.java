package org.xiaofan.sql.mybatis.dao;

import org.springframework.stereotype.Service;
import org.xiaofan.sql.spark.auto.TableModel.SparkSQL;

import java.util.List;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */
@Service("SparkSQLMapper")
public abstract class SparkSQLMapper {
    public abstract int deleteByPrimaryKey(Long paramLong);

    public abstract int insert(SparkSQL paramSPARK_SQL);

    public abstract int insertSelective(SparkSQL paramSPARK_SQL);

    public abstract SparkSQL selectByPrimaryKey(Long paramLong);

    public abstract List<SparkSQL> selectByAppId(Long paramLong);

    public abstract int updateByPrimaryKeySelective(SparkSQL paramSPARK_SQL);

    public abstract int updateByPrimaryKey(SparkSQL paramSPARK_SQL);
}

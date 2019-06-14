package org.xiaofan.sql.mybatis.dao;

import org.springframework.stereotype.Service;
import org.xiaofan.sql.spark.auto.TableModel.SparkRole;

import java.util.List;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */
@Service("SparkRoleMapper")
public abstract class SparkRoleMapper {
    public abstract int deleteByPrimaryKey(Long paramLong);

    public abstract int insert(SparkRole paramSPARK_ROLE);

    public abstract int insertSelective(SparkRole paramSPARK_ROLE);

    public abstract SparkRole selectByPrimaryKey(Long paramLong);

    public abstract List<SparkRole> selectAll();

    public abstract int updateByPrimaryKeySelective(SparkRole paramSPARK_ROLE);

    public abstract int updateByPrimaryKey(SparkRole paramSPARK_ROLE);
}

package org.xiaofan.sql.spark.auto.excutor;

import org.xiaofan.sql.spark.auto.TableModel.SparkSQL;

import java.util.List;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */
public  interface BaseExcutor {
    void Excutor(List<SparkSQL> paramList);

    void Excute(SparkSQL paramSparkSQL);
}

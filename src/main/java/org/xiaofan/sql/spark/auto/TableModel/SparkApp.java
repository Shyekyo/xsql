package org.xiaofan.sql.spark.auto.TableModel;

import java.io.Serializable;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */
public class SparkApp implements Serializable {
    private Long APP_ID;
    private String APP_NAME;
    private Short RUN_TYPE;
    private String START_TIME;
    private Long SPARK_CORE_NUMBER;

    public Long getAPP_ID()
    {
        return this.APP_ID;
    }

    public void setAPP_ID(Long APP_ID)
    {
        this.APP_ID = APP_ID;
    }

    public String getAPP_NAME()
    {
        return this.APP_NAME;
    }

    public void setAPP_NAME(String APP_NAME)
    {
        this.APP_NAME = (APP_NAME == null ? null : APP_NAME.trim());
    }

    public Short getRUN_TYPE()
    {
        return this.RUN_TYPE;
    }

    public void setRUN_TYPE(Short RUN_TYPE)
    {
        this.RUN_TYPE = RUN_TYPE;
    }

    public String getSTART_TIME()
    {
        return this.START_TIME;
    }

    public void setSTART_TIME(String START_TIME)
    {
        this.START_TIME = (START_TIME == null ? null : START_TIME.trim());
    }

    public Long getSPARK_CORE_NUMBER()
    {
        return this.SPARK_CORE_NUMBER;
    }

    public void setSPARK_CORE_NUMBER(Long SPARK_CORE_NUMBER)
    {
        this.SPARK_CORE_NUMBER = SPARK_CORE_NUMBER;
    }
}

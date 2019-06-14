package org.xiaofan.sql.spark.auto.TableModel;

import java.io.Serializable;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */
public class SparkSQL implements Serializable {
    private Long SQL_ID;
    private Long APP_ID;
    private String DATA_NAME;
    private String TYPE;
    private String TABLE_KEY;
    private Long ROLE_ID;
    private Long MIN;
    private Long MAX;
    private Long NUM_PARTITION;
    private Long OUT_TYPE;
    private String OUT_DIR;
    private Long OUT_MODE;
    private Long ACTION1;
    private String ACTION2;
    private String ACTION3;
    private String DATA;
    private String OPTIONS;
    private String CONDITIONS;

    public String getCONDITIONS()
    {
        return this.CONDITIONS;
    }

    public void setCONDITIONS(String cONDITIONS)
    {
        this.CONDITIONS = cONDITIONS;
    }

    public String getOPTIONS()
    {
        return this.OPTIONS;
    }

    public void setOPTIONS(String oPTIONS)
    {
        this.OPTIONS = oPTIONS;
    }

    public Long getSQL_ID()
    {
        return this.SQL_ID;
    }

    public void setSQL_ID(Long SQL_ID)
    {
        this.SQL_ID = SQL_ID;
    }

    public Long getAPP_ID()
    {
        return this.APP_ID;
    }

    public void setAPP_ID(Long APP_ID)
    {
        this.APP_ID = APP_ID;
    }

    public String getDATA_NAME()
    {
        return this.DATA_NAME;
    }

    public void setDATA_NAME(String DATA_NAME)
    {
        this.DATA_NAME = (DATA_NAME == null ? null : DATA_NAME.trim());
    }

    public String getTYPE()
    {
        return this.TYPE;
    }

    public void setTYPE(String TYPE)
    {
        this.TYPE = (TYPE == null ? null : TYPE.trim());
    }

    public String getTABLE_KEY()
    {
        return this.TABLE_KEY;
    }

    public void setTABLE_KEY(String TABLE_KEY)
    {
        this.TABLE_KEY = (TABLE_KEY == null ? null : TABLE_KEY.trim());
    }

    public Long getROLE_ID()
    {
        return this.ROLE_ID;
    }

    public void setROLE_ID(Long ROLE_ID)
    {
        this.ROLE_ID = ROLE_ID;
    }

    public Long getMIN()
    {
        return this.MIN;
    }

    public void setMIN(Long MIN)
    {
        this.MIN = MIN;
    }

    public Long getMAX()
    {
        return this.MAX;
    }

    public void setMAX(Long MAX)
    {
        this.MAX = MAX;
    }

    public Long getNUM_PARTITION()
    {
        return this.NUM_PARTITION;
    }

    public void setNUM_PARTITION(Long NUM_PARTITION)
    {
        this.NUM_PARTITION = NUM_PARTITION;
    }

    public Long getOUT_TYPE()
    {
        return this.OUT_TYPE;
    }

    public void setOUT_TYPE(Long OUT_TYPE)
    {
        this.OUT_TYPE = OUT_TYPE;
    }

    public String getOUT_DIR()
    {
        return this.OUT_DIR;
    }

    public void setOUT_DIR(String OUT_DIR)
    {
        this.OUT_DIR = (OUT_DIR == null ? null : OUT_DIR.trim());
    }

    public Long getOUT_MODE()
    {
        return this.OUT_MODE;
    }

    public void setOUT_MODE(Long OUT_MODE)
    {
        this.OUT_MODE = OUT_MODE;
    }

    public Long getACTION1()
    {
        return this.ACTION1;
    }

    public void setACTION1(Long ACTION1)
    {
        this.ACTION1 = ACTION1;
    }

    public String getACTION2()
    {
        return this.ACTION2;
    }

    public void setACTION2(String aCTION2)
    {
        this.ACTION2 = aCTION2;
    }

    public String getACTION3()
    {
        return this.ACTION3;
    }

    public void setACTION3(String aCTION3)
    {
        this.ACTION3 = aCTION3;
    }

    public String getDATA()
    {
        return this.DATA;
    }

    public void setDATA(String DATA)
    {
        this.DATA = (DATA == null ? null : DATA.trim());
    }

    public String toString()
    {
        return "SPARK_SQL [SQL_ID=" + this.SQL_ID + ", APP_ID=" + this.APP_ID + ", DATA_NAME=" + this.DATA_NAME + ", TYPE=" + this.TYPE + ", TABLE_KEY=" + this.TABLE_KEY + ", ROLE_ID=" + this.ROLE_ID + ", MIN=" + this.MIN + ", MAX=" + this.MAX + ", NUM_PARTITION=" + this.NUM_PARTITION + ", OUT_TYPE=" + this.OUT_TYPE + ", OUT_DIR=" + this.OUT_DIR + ", OUT_MODE=" + this.OUT_MODE + ", ACTION1=" + this.ACTION1 + ", ACTION2=" + this.ACTION2 + ", ACTION3=" + this.ACTION3 + ", DATA=" + this.DATA + ", OPTIONS=" + this.OPTIONS + ", CONDITIONS=" + this.CONDITIONS + "]";
    }
}

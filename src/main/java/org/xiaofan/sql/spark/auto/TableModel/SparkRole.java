package org.xiaofan.sql.spark.auto.TableModel;

import java.io.Serializable;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */
public class SparkRole implements Serializable {
    private Long ROLE_ID;
    private String USER_NAME;
    private String PASSWORD;
    private String URL;
    private String VERSION;
    private Short IF_DEFAULT_ROLE;

    public Long getROLE_ID()
    {
        return this.ROLE_ID;
    }

    public void setROLE_ID(Long ROLE_ID)
    {
        this.ROLE_ID = ROLE_ID;
    }

    public String getUSER_NAME()
    {
        return this.USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME)
    {
        this.USER_NAME = (USER_NAME == null ? null : USER_NAME.trim());
    }

    public String getPASSWORD()
    {
        return this.PASSWORD;
    }

    public void setPASSWORD(String PASSWORD)
    {
        this.PASSWORD = (PASSWORD == null ? null : PASSWORD.trim());
    }

    public String getURL()
    {
        return this.URL;
    }

    public void setURL(String URL)
    {
        this.URL = (URL == null ? null : URL.trim());
    }

    public String getVERSION()
    {
        return this.VERSION;
    }

    public void setVERSION(String VERSION)
    {
        this.VERSION = (VERSION == null ? null : VERSION.trim());
    }

    public Short getIF_DEFAULT_ROLE()
    {
        return this.IF_DEFAULT_ROLE;
    }

    public void setIF_DEFAULT_ROLE(Short IF_DEFAULT_ROLE)
    {
        this.IF_DEFAULT_ROLE = IF_DEFAULT_ROLE;
    }

    public String toString()
    {
        return "SparkRole [ROLE_ID=" + this.ROLE_ID + ", USER_NAME=" + this.USER_NAME + ", PASSWORD=" + this.PASSWORD + ", URL=" + this.URL + ", VERSION=" + this.VERSION + ", IF_DEFAULT_ROLE=" + this.IF_DEFAULT_ROLE + "]";
    }
}

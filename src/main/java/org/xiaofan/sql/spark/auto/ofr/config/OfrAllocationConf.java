package org.xiaofan.sql.spark.auto.ofr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */
@Component("ofrAllocationConf")
public class OfrAllocationConf {
    @Value("${jdbc.url}")
    public String IamUrl;
    @Value("${jdbc.username}")
    public String IamUser;
    @Value("${jdbc.password}")
    public String IamPass;
    @Value("${hdfs}")
    public String hdfs;
    @Value("${app.home}")
    public String appHome;
}

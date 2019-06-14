package org.xiaofan.sql.springContext;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */
public class SpringContextTool {
    final static Logger logger = Logger.getLogger(SpringContextTool.class);
    public static ApplicationContext context;

    public static ApplicationContext getBeanContext() {
        if (logger.isDebugEnabled()) logger.debug("获得spring-bean上下文");
        try {
            if (context == null) {
                logger.info("初始化spring-bean上下文");
                context = new ClassPathXmlApplicationContext("/springContext.xml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }
}

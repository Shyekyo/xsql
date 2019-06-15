package org.xiaofan.sql.spark.auto.server;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.jdbc.JdbcDialects;
import org.xiaofan.sql.mybatis.dao.SparkAppMapper;
import org.xiaofan.sql.mybatis.dao.SparkRoleMapper;
import org.xiaofan.sql.mybatis.dao.SparkSQLMapper;
import org.xiaofan.sql.spark.auto.TableModel.SparkRole;
import org.xiaofan.sql.spark.auto.TableModel.SparkSQL;
import org.xiaofan.sql.spark.auto.excutor.SqlExcutor;
import org.xiaofan.sql.spark.auto.ofr.conf.SparkConfUtil;
import org.xiaofan.sql.spark.auto.ofr.config.OfrAllocationConf;
import org.xiaofan.sql.spark.auto.ofr.config.OracleDialect;
import org.xiaofan.sql.springContext.SpringContextTool;

import java.util.*;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */
public class AutoSparkSql {
    private static Logger logger = Logger.getLogger(AutoSparkSql.class);

    public static void main(String[] args)
    {
        List<Map<String, String>> sparkOptions = new ArrayList();
        String appId = "3";
        if (args.length > 0) {
            appId = args[0];
        }
        System.out.println("spark sql >>>>>>>>>>>>>>>>>>>>>>");
        OfrAllocationConf ofrAllocationConf = (OfrAllocationConf) SpringContextTool.context.getBean("ofrAllocationConf");
        SparkConf conf = new SparkConf().setAppName(appId);
        conf.set("spark.memory.storageFraction", "0.4");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SparkSession spark = SparkSession.builder().
                appName(appId).
                config("spark.sql.inMemoryColumnarStorage.batchSize", "1000000").
                config("spark.sql.shuffle.partitions", "150").
                config("spark.sql.autoBroadcastJoinThreshold", "31457280").
                getOrCreate();

        System.out.println("spark 配置信息：\n" + spark.conf().getAll());
        spark.sparkContext().setCheckpointDir(ofrAllocationConf.hdfs + "/check");

        SparkConfUtil.sqlContextInit(spark);

        //JdbcDialects.registerDialect(new OracleDialect());

        SparkAppMapper sparkAppMapper = (SparkAppMapper)SpringContextTool.context.getBean("sparkAppMapper");

        SparkSQLMapper sparkSQLMapper = (SparkSQLMapper)SpringContextTool.context.getBean("sparkSQLMapper");

        String url = ofrAllocationConf.IamUrl;

        Map<String, String> options = new HashMap<>();
        options.put("user", ofrAllocationConf.IamUser);
        options.put("password", ofrAllocationConf.IamPass);
        options.put("driver", "oracle.jdbc.OracleDriver");
        Properties ps = new Properties();
        ps.putAll(options);
        //根据appid找到对应的任务，sqlid是有顺序的
        List<SparkSQL> spark_sqls = sparkSQLMapper.selectByAppId(Long.valueOf(appId));

        SqlExcutor sqlExcutor = (SqlExcutor)SpringContextTool.context.getBean("sqlExcutor");

        sqlExcutor.setSpark(spark);

        sqlExcutor.Excutor(spark_sqls);
        spark.stop();
    }
    public static void testMybatis()
    {
        SparkSQLMapper sPARK_SQLMapper = (SparkSQLMapper)SpringContextTool.context.getBean("sparkSQLMapper");
        List<SparkSQL> spark_sqls = sPARK_SQLMapper.selectByAppId(Long.valueOf(151L));

        SparkRoleMapper sparkRoleMapper = (SparkRoleMapper)SpringContextTool.context.getBean("sparkRoleMapper");
        List<SparkRole> spark_roles = sparkRoleMapper.selectAll();
        for (SparkRole r : spark_roles) {
            System.out.println(">>>" + r.toString());
        }
    }
}

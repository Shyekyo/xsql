package org.xiaofan.sql.spark.auto.excutor;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.DataFrameWriter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.storage.StorageLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.xiaofan.sql.mybatis.dao.SparkRoleMapper;
import org.xiaofan.sql.spark.auto.TableModel.SparkRole;
import org.xiaofan.sql.spark.auto.TableModel.SparkSQL;
import org.xiaofan.sql.springContext.SpringContextTool;

@Service("sqlExcutor")
public class SqlExcutor
        implements BaseExcutor
{
    @Value("${hdfs}")
    public String hdfs;
    public static Map<Long, SparkRole> rolesMap;
    public static final CharSequence mysqlChar = "mysql";
    static Map<String, String> paras = new HashMap();
    SparkSession spark;
    Map<String, Dataset<Row>> dfMap = new HashMap();
    //根据appid找到按照sqlid排序后找到一系列sql
    public void Excutor(List<SparkSQL> spark_sqls)
    {
        SparkRoleMapper sparkRoleMapper = (SparkRoleMapper)SpringContextTool.context.getBean("sparkRoleMapper");
        //找到所有的用户
        List<SparkRole> roles = sparkRoleMapper.selectAll();
        rolesMap = new HashMap();
        for (SparkRole r : roles) {
            rolesMap.put(r.getROLE_ID(), r);
        }
        System.out.println(">>>>roles count:" + roles.size() + "  " + rolesMap.size());
        SparkRole roletest = (SparkRole)rolesMap.get(Long.valueOf(0L));
        System.out.println(">>>>>>>>>>>>>>>default userrole is " + roletest.getURL());
        for (int i = 0; i < spark_sqls.size(); i++) {
            Excute((SparkSQL) spark_sqls.get(i));
        }
    }

    public void Excute(SparkSQL spark_sql)
    {
        try
        {
            Dataset<Row> df = null;
            Dataset<Row> check_df = null;

            String type = spark_sql.getTYPE();
            String data = enterToSpace(spark_sql.getDATA());
            String dataName = enterToSpace(spark_sql.getDATA_NAME());
            Long role_id = Long.valueOf(spark_sql.getROLE_ID() == null ? 0L : spark_sql.getROLE_ID().longValue());
            Long out_type = spark_sql.getOUT_TYPE();
            String out_dir = spark_sql.getOUT_DIR();
            Long out_mode = Long.valueOf(spark_sql.getOUT_MODE() == null ? 1L : spark_sql.getOUT_MODE().longValue());
            String optionsCol = spark_sql.getOPTIONS();
            String condition = spark_sql.getCONDITIONS();
            if (condition != null)
            {
                JexlEngine jexl = new JexlEngine();

                String jexlExp = enterToSpace(condition);
                Expression ee = jexl.createExpression(jexlExp);

                JexlContext jc = new MapContext();
                if (ee.evaluate(jc).equals(Boolean.valueOf(false))) {
                    return;
                }
            }
            SaveMode savemode;
            if (out_mode.longValue() == 2L)
            {
                savemode = SaveMode.Ignore;
            }
            else
            {
                if (out_mode.longValue() == 3L) {
                    savemode = SaveMode.Append;
                } else {
                    savemode = SaveMode.Overwrite;
                }
            }
            Long action1 = spark_sql.getACTION1();
            String action2 = spark_sql.getACTION2();
            String action3 = spark_sql.getACTION3();

            SparkRole role = (SparkRole)rolesMap.get(role_id);
            String url = role.getURL();

            Properties ps = new Properties();
            Map<String, String> options = new HashMap();
            options.put("user", role.getUSER_NAME());
            options.put("password", role.getPASSWORD());
            if (url.contains(mysqlChar)) {
                options.put("driver", "com.mysql.jdbc.Driver");
            } else {
                options.put("driver", "oracle.jdbc.OracleDriver");
            }
            ps.putAll(options);

            System.out.println("id " + spark_sql.getSQL_ID() + "  type " + type + "  dataName " + dataName + " data :\n" + data);
            DataFrameReader read;
            //String str3;
            int str3;
            //String s;
            int s;
            String[] dfs;
            String unpersist_sql;
            switch (type)
            {
                case "ora_table":
                    this.dfMap.put(dataName, this.spark.read().jdbc(url, data, ps));
                    df = (Dataset)this.dfMap.get(dataName);
                    df.createOrReplaceTempView(dataName);
                    df.printSchema();
                    df.cache();
                    System.out.println(" df " + dataName + " count " + df.count());

                    df.show();
                    if ((action2 != null) && (action2 != "")) {
                        df.selectExpr(action2.split(",")).show();
                    }
                    if ((action3 != null) && (action3 != "")) {
                        df.selectExpr(action3.split(",")).show();
                    }
                    break;
                case "mysql_table":
                    this.dfMap.put(dataName, this.spark.read().jdbc(url, data, ps));
                    df = (Dataset)this.dfMap.get(dataName);
                    df.createOrReplaceTempView(dataName);
                    df.printSchema();
                    df.cache();
                    System.out.println(" df " + dataName + " count " + df.count());

                    df.show();
                    if ((action2 != null) && (action2 != "")) {
                        df.selectExpr(action2.split(",")).show();
                    }
                    if ((action3 != null) && (action3 != "")) {
                        df.selectExpr(action3.split(",")).show();
                    }
                    break;
                case "hive_table":
                    break;
                case "para":
                    if ((dataName != null) && (data != null)) {
                        paras.put(dataName, data);
                    }
                    break;
                case "sql":
                    df = this.spark.sql(data).coalesce(600);
                    df.createOrReplaceTempView(dataName);
                    df.persist(StorageLevel.MEMORY_AND_DISK());
                    System.out.println(" df " + dataName + " count " + df.count());
                    df.printSchema();
                    if (action1 != null) {
                        df.show();
                    }
                    if ((action2 != null) && (action2 != "")) {
                        df.selectExpr(action2.split(",")).show();
                    }
                    if ((action3 != null) && (action3 != "")) {
                        df.selectExpr(action3.split(",")).show();
                    }
                    break;
                case "sql-checkpoint":
                    data = enterToSpace(data);
                    check_df = this.spark.sql(data).coalesce(600);
                    JavaRDD<Row> check_rdd = check_df.toJavaRDD();
                    check_rdd.checkpoint();

                    df = this.spark.createDataFrame(check_rdd, check_df.schema());

                    df.createOrReplaceTempView(dataName);
                    System.out.println(" >>>>>>>>>>>>>>>>>>>>checkpoint is  ");

                    System.out.println(" logicalPlan " + df.logicalPlan());
                    df.persist(StorageLevel.MEMORY_AND_DISK());
                    System.out.println(" df  " + dataName + " count " + df.count());
                    df.printSchema();
                    if (action1 != null) {
                        df.show();
                    }
                    if ((action2 != null) && (action2 != "")) {
                        df.selectExpr(action2.split(",")).show();
                    }
                    if ((action3 != null) && (action3 != "")) {
                        df.selectExpr(action3.split(",")).show();
                    }
                    break;
                case "val":
                    break;
                case "hdfs-par":
                    this.dfMap.put(dataName, this.spark.read().parquet(data));
                    df = (Dataset)this.dfMap.get(dataName);
                    df.createOrReplaceTempView(dataName);
                    df.cache();
                    System.out.println(" df " + dataName + " count " + df.count());
                    df.printSchema();
                    df.show();
                    if ((action2 != null) && (action2 != "")) {
                        df.selectExpr(action2.split(",")).show();
                    }
                    if ((action3 != null) && (action3 != "")) {
                        df.selectExpr(action3.split(",")).show();
                    }
                    break;
                case "res-hdfs-par":
                    System.out.println(" write [" + dataName + "]  to oracle >>>>>>>>>>>>>>>");
                    df = this.spark.read().parquet(data).repartition(20);

                    df.write()
                            .mode(savemode)
                            .jdbc(url, dataName, ps);
                    if ((action2 != null) && (action2 != "")) {
                        df.selectExpr(action2.split(",")).show();
                    }
                    if ((action3 != null) && (action3 != "")) {
                        df.selectExpr(action3.split(",")).show();
                    }
                    break;
                case "res2-hdfs-par":
                    System.out.println(" write [" + dataName + "]  to oracle >>>>>>>>>>>>>>>");
                    df = this.spark.read().parquet(data);

                    df.write()
                            .mode(savemode)
                            .jdbc(url, dataName, ps);
                    if ((action2 != null) && (action2 != "")) {
                        df.selectExpr(action2.split(",")).show();
                    }
                    if ((action3 != null) && (action3 != "")) {
                        df.selectExpr(action3.split(",")).show();
                    }
                    break;
                case "hdfs-csv":
                    read = this.spark.read();
                    if ((optionsCol != null) && (optionsCol.length() > 0))
                    {
                        String[] optionsCloSplit = optionsCol.split(";");
                        String[] arrayOfString1 = optionsCloSplit;int j = arrayOfString1.length;
                        for (str3 = 0; str3 < j; str3++)
                        {
                            //s = arrayOfString1[str3];
                            String s1 = arrayOfString1[str3];
                            String[] ss = s1.split(":");
                            System.out.println("k : " + ss[0] + "    v   :" + ss[1]);
                            read.option(ss[0], ss[1]);
                        }
                    }
                    else
                    {
                        read.option("header", "true");
                    }
                    this.dfMap.put(dataName, read.csv(data));
                    df = (Dataset)this.dfMap.get(dataName);
                    df.createOrReplaceTempView(dataName);
                    df.cache();
                    System.out.println(" df " + dataName + " count " + df.count());
                    df.printSchema();
                    df.show();
                    if ((action2 != null) && (action2 != "")) {
                        df.selectExpr(action2.split(",")).show();
                    }
                    if ((action3 != null) && (action3 != "")) {
                        df.selectExpr(action3.split(",")).show();
                    }
                    break;
                case "hdfs-file":
                    break;
                case "hdfs-json":
                    break;
                case "unpersist":
                    dfs = getUnpersistDf(data);
                    String[] arrayOfString2 = dfs;str3 = arrayOfString2.length;
                    for (s = 0; s < str3; s++)
                    {
                        String t = arrayOfString2[s];

                        unpersist_sql = "uncache table " + t;
                        System.out.println(unpersist_sql);
                        try
                        {
                            this.spark.sql(unpersist_sql);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    System.out.println(" [type] in table 'spark_sql' cannot be null wangwangwang........................");
            }
            System.out.println(" jieshu........................");
            if ((out_type != null) && (!type.equals("val")))
            {
                System.out.println(" [out_dir] " + out_type);

                Object write = df.write().mode(savemode);
                if ((optionsCol != null) && (optionsCol.length() > 0))
                {
                    /*
                    optionsCloSplit = optionsCol.split(";");
                    //read = (DataFrameReader)optionsCloSplit;unpersist_sql = read.length;
                    DataFrameReader read2 = (DataFrameReader)optionsCloSplit;unpersist_sql = read2.length;
                    for ( int str2 = 0; str2 < unpersist_sql; str2++)
                    {
                        String s = read[str2];
                        String[] ss = s.split(":");
                        System.out.println("k : " + ss[0] + "    v   :" + ss[1]);
                        write = ((DataFrameWriter)write).option(ss[0], ss[1]);
                    }*/
                    //TODO 这段代码有问题
                }
                else
                {
                    write = ((DataFrameWriter)write).option("header", "true");
                }
                String optionsCloSplit = out_type.toString();
                //read = -1;
                int read1 =-1;
                switch (((String)optionsCloSplit).hashCode())
                {
                    case 49:
                        if (((String)optionsCloSplit).equals("1")) {
                            //read = 0;
                            read1 =0;
                        }
                        break;
                    case 50:
                        if (((String)optionsCloSplit).equals("2")) {
                            //read = 1;
                            read1 =0;
                        }
                        break;
                    case 51:
                        if (((String)optionsCloSplit).equals("3")) {
                            //read = 2;
                            read1 =0;
                        }
                        break;
                }
                //switch (read)
                switch (read1)
                {
                    case 0:
                        System.out.println(" write [" + dataName + "]  to oracle >>>>>>>>>>>>>>>");
                        ((DataFrameWriter)write)
                                .jdbc(url, dataName, ps);
                        break;
                    case 1:
                        if (null == out_dir) {
                            out_dir = this.hdfs + "/sqoop/" + dataName;
                        }
                        System.out.println(" write [" + dataName + "]  as parquet to hdfs  >>>>>>>>>>>>>>>");
                        ((DataFrameWriter)write)
                                .parquet(enterToSpace(out_dir));
                        break;
                    case 2:
                        if (null == out_dir)
                        {
                            out_dir = this.hdfs + "/spark2/csv/" + dataName;
                            System.out.println("[out_dir]  is null  using default dir: \n" + out_dir);
                        }
                        System.out.println(" write [" + dataName + "]  as csv to hdfs  >>>>>>>>>>>>>>>");
                        ((DataFrameWriter)write)
                                .csv(enterToSpace(out_dir));
                        break;
                }
                System.out.println(" jieshu........................");
            }
        }
        catch (Exception e)
        {
            e = e;
            e.printStackTrace();this.spark.stop();System.exit(0);
        }
        finally {}
    }

    public SparkSession getSpark()
    {
        return this.spark;
    }

    public void setSpark(SparkSession spark)
    {
        this.spark = spark;
    }

    public Map<String, Dataset<Row>> getDfMap()
    {
        return this.dfMap;
    }

    public void setDfMap(Map<String, Dataset<Row>> dfMap)
    {
        this.dfMap = dfMap;
    }

    public static String enterToSpace(String myString)
    {
        String newString = myString;
        Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
        Matcher m = CRLF.matcher(myString);
        if (m.find())
        {
            System.out.println("e ...");
            newString = m.replaceAll(" ");
        }
        System.out.println("a ...");
        for (String para : paras.keySet())
        {
            CRLF = Pattern.compile(String.format("(#%s#)", new Object[] { para }));
            m = CRLF.matcher(newString);
            if (m.find())
            {
                System.out.println("替换参数 " + para);
                newString = m.replaceAll((String)paras.get(para));
            }
        }
        return newString;
    }

    public static String[] getUnpersistDf(String dfs)
    {
        return dfs.split(",");
    }

    public static void main(String[] args)
    {
        paras.put("proflag", "1");
        JexlEngine jexl = new JexlEngine();

        String jexlExp = enterToSpace("#proflag#==1");
        Expression ee = jexl.createExpression(jexlExp);


        JexlContext jc = new MapContext();



        System.out.println("?>>>>>>>>>>" + ee.evaluate(jc).equals(Boolean.valueOf(false)));


        paras.put("ROOT_DIR", "hdfs://iotcluster/apps/iot/billdiv/201706");
        String aaa = "#ROOT_DIR#/rptbill";
        System.out.println(aaa);

        String newStr = enterToSpace(aaa);
        System.out.println(newStr);

        String data = "asse_mss_file_emf_mapping,asse_ds_mapping";

        String[] dfs = getUnpersistDf(data);
        for (String t : dfs)
        {
            String unpersist_sql = "uncache table " + t;
            System.out.println(unpersist_sql);
        }
        Object dfMap = new HashMap();
        ((Map)dfMap).put("1", "1");
        ((Map)dfMap).put("12", "12");
        ((Map)dfMap).put("13", "13");
        ((Map)dfMap).put("1", "13");
        ((Map)dfMap).put("14", "14");

        ((Map)dfMap).remove("14");
        ((Map)dfMap).clear();
        System.out.format("map " + dfMap.toString(), new Object[0]);
    }
}


package org.xiaofan.sql.spark.auto.ofr.conf;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.api.java.UDF3;
import org.apache.spark.sql.types.DataTypes;
import scala.collection.JavaConversions;
import scala.collection.mutable.WrappedArray;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */
public class SparkConfUtil {
    public static void sqlContextInit(SparkSession sqlContext)
    {
        sqlContext.udf().register("strLen", new UDF1()
        {
            public Integer call(Object s)
                    throws Exception
            {
                String sV = String.valueOf(s);
                return Integer.valueOf(sV.length());
            }
        }, DataTypes.IntegerType);

        sqlContext.udf().register("replaceMy", new UDF1()
        {
            public String call(Object r)
                    throws Exception
            {
                if (null == r) {
                    return null;
                }
                String rV = String.valueOf(r);
                rV = rV.replace(",", "|");
                return rV;
            }
        }, DataTypes.StringType);


        sqlContext.udf().register("replace", new UDF3()
        {
            public String call(Object r, Object t, Object z)
                    throws Exception
            {
                if (null == r) {
                    return null;
                }
                String rV = String.valueOf(r);
                String tV = String.valueOf(t);
                String zV = String.valueOf(z);
                rV = rV.replace(tV, zV);
                return rV;
            }
        }, DataTypes.StringType);
        sqlContext.udf().register("realFeeType", new UDF2()
        {
            @Override
            public Object call(Object o, Object o2) throws Exception {
                try
                {  //WrappedArray<String>
                    List<String> realFees = new ArrayList();
                    WrappedArray a = (WrappedArray)o;
                    WrappedArray b = (WrappedArray)o2;
                    String defaultFee = (String)a.head();
                    realFees.add(0, defaultFee);

                    List<String> testSeqToList = JavaConversions.seqAsJavaList(a);
                    testSeqToList = new ArrayList(testSeqToList);
                    testSeqToList.remove(0);
                    List<String> testSeqToList2 = JavaConversions.seqAsJavaList(b);
                    testSeqToList.retainAll(testSeqToList2);
                    for (String sss : testSeqToList) {
                        System.out.print(sss);
                    }
                    realFees.addAll(testSeqToList);

                    Object buffer = JavaConversions.asScalaBuffer(realFees);
                    return JavaConversions.asScalaBuffer(realFees).toIndexedSeq();
                }
                catch (Exception e)
                {
                    System.out.println("realFeeType    >>>>>>>>>>>>>>>>>>>>");
                }
                return null;
            }
        }, DataTypes.createArrayType(DataTypes.StringType));

        sqlContext.udf().register("truncdd", new UDF1()
        {
            public java.sql.Date call(Object a)
                    throws Exception
            {
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                if (null == a) {
                    return null;
                }
                if ((a instanceof java.sql.Date)) {
                    return (java.sql.Date)a;
                }
                if ((a instanceof java.util.Date)) {
                    return new java.sql.Date(df.parse(df.format((java.util.Date)a)).getTime());
                }
                return null;
            }
        }, DataTypes.DateType);

        sqlContext.udf().register("truncmm", new UDF1()
        {
            public java.sql.Date call(Object a)
                    throws Exception
            {
                DateFormat df = new SimpleDateFormat("yyyyMM");
                if (null == a) {
                    return null;
                }
                if ((a instanceof java.sql.Date)) {
                    return (java.sql.Date)a;
                }
                if ((a instanceof java.util.Date)) {
                    return new java.sql.Date(df.parse(df.format((java.util.Date)a)).getTime());
                }
                return null;
            }
        }, DataTypes.DateType);

        sqlContext.udf().register("to_num", new UDF1()
        {
            public BigDecimal call(Object r)
                    throws Exception
            {
                if (null == r) {
                    return new BigDecimal("0");
                }
                if ((r instanceof String)) {
                    return new BigDecimal((String)r);
                }
                if ((r instanceof Double)) {
                    return BigDecimal.valueOf(((Double)r).doubleValue());
                }
                if ((r instanceof Long)) {
                    return BigDecimal.valueOf(((Long)r).longValue());
                }
                return new BigDecimal(r.toString());
            }
        }, DataTypes.createDecimalType(20, 0));

        sqlContext.udf().register("to_num_2", new UDF1()
        {
            public BigDecimal call(Object r)
                    throws Exception
            {
                if (null == r) {
                    return new BigDecimal("0");
                }
                if ((r instanceof String)) {
                    return new BigDecimal((String)r);
                }
                if ((r instanceof Double)) {
                    return BigDecimal.valueOf(((Double)r).doubleValue());
                }
                if ((r instanceof Long)) {
                    return BigDecimal.valueOf(((Long)r).longValue());
                }
                return new BigDecimal(r.toString());
            }
        }, DataTypes.createDecimalType(20, 2));
        sqlContext.udf().register("to_num_1", new UDF1()
        {
            public BigDecimal call(Object r)
                    throws Exception
            {
                if (null == r) {
                    return new BigDecimal("0");
                }
                if ((r instanceof String)) {
                    return new BigDecimal((String)r);
                }
                if ((r instanceof Double)) {
                    return BigDecimal.valueOf(((Double)r).doubleValue());
                }
                if ((r instanceof Long)) {
                    return BigDecimal.valueOf(((Long)r).longValue());
                }
                return new BigDecimal(r.toString());
            }
        }, DataTypes.createDecimalType(20, 1));


        sqlContext.udf().register("to_char", new UDF1()
        {
            public String call(Object r)
                    throws Exception
            {
                if (r == null) {
                    return null;
                }
                return r.toString();
            }
        }, DataTypes.StringType);

        sqlContext.udf().register("long_date", new UDF1()
        {
            public Timestamp call(Object r)
                    throws Exception
            {
                if (null == r)
                {
                    java.util.Date date = new java.util.Date();
                    return new Timestamp(date.getTime());
                }
                Long aLong = (Long)r;
                return new Timestamp(aLong.longValue());
            }
        }, DataTypes.TimestampType);

        sqlContext.udf().register("to_dateMy", new UDF1()
        {
            public Timestamp call(Object r)
                    throws Exception
            {
                if (null == r)
                {
                    java.util.Date date = new java.util.Date();
                    return new Timestamp(date.getTime());
                }
                String r1 = (String)r;
                DateFormat sdf = new SimpleDateFormat("yyyyMM");
                java.util.Date date = sdf.parse(r1);
                return new Timestamp(date.getTime());
            }
        }, DataTypes.TimestampType);

        sqlContext.udf().register("to_date_ym", new UDF1()
        {
            public java.sql.Date call(Object r)
                    throws Exception
            {
                if (null == r)
                {
                    java.util.Date date = new java.util.Date();
                    return new java.sql.Date(date.getTime());
                }
                String rs = r.toString();
                DateFormat sdf = new SimpleDateFormat("yyyyMM");
                java.util.Date date = sdf.parse(rs);
                return new java.sql.Date(date.getTime());
            }
        }, DataTypes.DateType);

        sqlContext.udf().register("to_date_ymd", new UDF1()
        {
            public java.sql.Date call(Object r)
                    throws Exception
            {
                if (null == r)
                {
                    java.util.Date date = new java.util.Date();
                    return new java.sql.Date(date.getTime());
                }
                String rs = r.toString();
                DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                java.util.Date date = sdf.parse(rs);
                return new java.sql.Date(date.getTime());
            }
        }, DataTypes.DateType);

        sqlContext.udf().register("to_dateSMy", new UDF1()
        {
            public Timestamp call(Object r)
                    throws Exception
            {
                if (null == r)
                {
                    java.util.Date date = new java.util.Date();
                    return new Timestamp(date.getTime());
                }
                String r1 = (String)r;
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date date = sdf.parse(r1);


                return new Timestamp(date.getTime());
            }
        }, DataTypes.TimestampType);

        sqlContext.udf().register("arrayCount", new UDF1()
        {
            public Integer call(Object r)
                    throws Exception
            {
                WrappedArray r1 = (WrappedArray)r;
                return Integer.valueOf(r1.length());
            }
        }, DataTypes.IntegerType);

        sqlContext.udf().register("arrayCount", new UDF1()
        {
            public String call(Object r)
                    throws Exception
            {
                try
                {
                    WrappedArray r1 = (WrappedArray)r;
                    return r1.toString().trim().replaceAll(",", ":").replaceFirst("WrappedArray", "");
                }
                catch (Exception e) {}
                return "";
            }
        }, DataTypes.StringType);

        sqlContext.udf().register("to_dateT", new UDF1()
        {
            public Timestamp call(Object r)
                    throws Exception
            {
                if (null == r)
                {
                    java.util.Date date = new java.util.Date();
                    return new Timestamp(date.getTime());
                }
                String r1=(String)r;
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = sdf.parse(r1);


                return new Timestamp(date.getTime());
            }
        }, DataTypes.TimestampType);

        sqlContext.udf().register("decodeOfr", new UDF1()
        {
            public String call(Object r)
                    throws Exception
            {
                if (null == r) {
                    return "N";
                }
                String r1=(String)r;
                if ((r1.equalsIgnoreCase("Y")) || (r1.equalsIgnoreCase("Z"))) {
                    return "Y";
                }
                return r1;
            }
        }, DataTypes.StringType);
    }

    public static void main(String... args)
    {
        Double db = Double.valueOf(2.16D);
        System.out.println(db.toString());
    }
}

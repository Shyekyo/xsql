package org.xiaofan.sql.spark.auto.ofr.config;

import org.apache.spark.sql.jdbc.JdbcDialects;
import org.apache.spark.sql.jdbc.JdbcType;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.MetadataBuilder;
import scala.Option;
import scala.Some;

/**
 * Created by zhangxiaofan on 2019/6/14.
 */
public class OracleDialect{

   /*OracleDialect public boolean canHandle(String url)
    {
        return url.contains("oracle");
    }
    public Option<JdbcType> getJDBCType(DataType dt)
    {
        if (dt.sameType(DataTypes.StringType)) {
            return new Some(new JdbcType("VARCHAR2(2000)", -16));
        }
        if (dt.sameType(DataTypes.LongType))
        {
            System.out.println("get datatype  :  " + dt.toString());
            return new Some(new JdbcType("NUMBER(19)", 2));
        }
        if (dt.sameType(DataTypes.IntegerType))
        {
            System.out.println("get datatype  :  " + dt.toString());
            return new Some(new JdbcType("NUMBER(19)", 2));
        }
        if (dt.sameType(DataTypes.createDecimalType(0, -127)))
        {
            System.out.println("get datatype  :  " + DataTypes.createDecimalType(0, -127).toString());
            return new Some(new JdbcType("NUMBER(23)", 2));
        }
        return super.getJDBCType(dt);
    }

    public Option<DataType> getCatalystType(int sqlType, String typeName, int size, MetadataBuilder md)
    {
        Long scale = Long.valueOf(null != md ? md.build().getLong("scale") : 0L);
        if ((typeName.equalsIgnoreCase("number")) && (size == 0))
        {
            System.out.println(" number " + sqlType + " typename " + typeName + " size :" + size + "  scale " + scale);
            return new Some(DataTypes.createDecimalType(20, 0));
        }
        if ((typeName.equalsIgnoreCase("number")) && (size == 1))
        {
            System.out.println(" number " + sqlType + " typename " + typeName + " size :" + size + "    scale " + scale);

            return new Some(DataTypes.createDecimalType(1, scale.intValue()));
        }
        return super.getCatalystType(sqlType, typeName, size, md);
    }*/
}

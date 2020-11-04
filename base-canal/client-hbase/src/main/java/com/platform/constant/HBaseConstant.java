package com.platform.constant;


import org.apache.hadoop.hbase.util.Bytes;

/**
 * HBaseConstant class
 *
 * @author wulinhao
 * @date 2020/09/28
 */
public class HBaseConstant {

    /**
     *     Hbase B次集团薪资表
     */
    public static final String TABLE_FACTORY_BGROUP_SALARY = "FACTORY_BGROUP_SALARY";


    /**
     *     B次集团薪资表 FAMILY
     */
    public static final byte[] FAMILY_FACTORY_BGROUP_SALARY = Bytes.toBytes("FACTORY_BGROUP_SALARY_INFO");


    /**
     *     Hbase FII CESGB薪资数据表
     */
    public static final String TABLE_FACTORY_FII_CESGB_SALARY = "FACTORY_FII_CESGB_SALARY";


    /**
     *     FII CESGB薪资数据表 FAMILY
     */
    public static final byte[] FAMILY_FACTORY_FII_CESGB_SALARY = Bytes.toBytes("FACTORY_FII_CESGB_SALARY_INFO");


    /**
     *     FII CNSGB薪资数据表
     */
    public static final String TABLE_FACTORY_FII_CNSGB_TSBG_SALARY = "FACTORY_FII_CNSGB_TSBG_SALARY";


    /**
     *     FII CNSGB薪资数据表 FAMILY
     */
    public static final byte[] FAMILY_FACTORY_FII_CNSGB_TSBG_SALARY = Bytes.toBytes("FACTORY_FII_CNSGB_TSBG_SALARY_INFO");


    /**
     *     H次集团薪资数据一表
     */
    public static final String TABLE_FACTORY_H_GROUP_ONE_SALARY = "FACTORY_H_GROUP_ONE_SALARY";


    /**
     *     H次集团薪资数据一表 FAMILY
     */
    public static final byte[] FAMILY_FACTORY_H_GROUP_ONE_SALARY = Bytes.toBytes("FACTORY_H_GROUP_ONE_SALARY_INFO");



    /**
     *     IDPBG薪资数据表
     */
    public static final String TABLE_FACTORY_IDPG_IDPBG_SALARY = "FACTORY_IDPG_IDPBG_SALARY";


    /**
     *     IDPBG薪资数据表 FAMILY
     */
    public static final byte[] FAMILY_FACTORY_IDPG_IDPBG_SALARY = Bytes.toBytes("FACTORY_IDPG_IDPBG_SALARY_INFO");


    /**
     *     D次 PCEBG薪资数据表
     */
    public static final String TABLE_FACTORY_D_GROUP_NEW_PCEBG_SALARY = "FACTORY_D_GROUP_NEW_PCEBG_SALARY";


    /**
     *     D次 PCEBG薪资数据表 FAMILY
     */
    public static final byte[] FAMILY_FACTORY_D_GROUP_NEW_PCEBG_SALARY = Bytes.toBytes("FACTORY_D_GROUP_NEW_PCEBG_SALARY_INFO");



    /**
     *     IPEBG薪资数据表
     */
    public static final String TABLE_FACTORY_IPEBG_SALARY = "FACTORY_IPEBG_SALARY";


    /**
     *     IPEBG薪资数据表 FAMILY
     */
    public static final byte[] FAMILY_FACTORY_IPEBG_SALARY_INFO = Bytes.toBytes("FACTORY_IPEBG_SALARY_INFO");

    /**
     * J次，S次，其它次。薪资数据表 FAMILY
     */
    public static final byte[] FAMILY_FACTORY_JSO_SALARY_INFO = Bytes.toBytes("FACTORY_JSO_SALARY_INFO");

    /**
     *
     * J次，S次，其它次。薪资数据表
     */
    public static final String TABLE_FACTORY_JSO_SALARY="FACTORY_JSO_SALARY";

    /**
     * ACKN 薪资数据表 FAMILY
     */
    public static final byte[] FAMILY_FACTORY_NON_A_LX_SALARY_INFO = Bytes.toBytes("FACTORY_NON_A_LX_SALARY_INFO");

    /**
     * ACKN 薪资数据表
     */
    public static final String TABLE_FACTORY_NON_A_LX_SALARY="FACTORY_NON_A_LX_SALARY";


    /**
     * BBE 沃客考勤原始数据表 FAMILY
     */
    public static final byte[] FAMILY_BBE_WOKE_ATTENDANCE_INFO = Bytes.toBytes("BBE_WOKE_ATTENDANCE_INFO");

    /**
     * BBE 沃客考勤原始数据表
     */
    public static final String TABLE_BBE_WOKE_ATTENDANCE ="BBE_WOKE_ATTENDANCE";

}

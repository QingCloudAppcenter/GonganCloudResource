package paas.common.utils;

import java.util.HashMap;
import java.util.Map;

public class ParaConstant {
    public static Map<String,String>  CALSPECIFYMAP; /**根据内存和CPU获取计算规格**/
    public static Map<String,String>  CPUMEMOERYMAP; /**根据计算规格获取内存和cpu*/
    public static Map<String,String>  G2Q_SERVICETYPEMAP; /**公安_青云服务类型映射*/
    public static Map<String,String>  Q2G_SERVICETYPEMAP; /**青云_公安服务类型映射*/
    public static Map<String,String>  SERVICETYPE_MAINROLE_MAP; /**青云_公安服务类型映射*/

    static {
        CALSPECIFYMAP = new HashMap<String,String>();
        CALSPECIFYMAP.put("1-2","0001");
        CALSPECIFYMAP.put("1-4","0002");
        CALSPECIFYMAP.put("1-8","0003");
        CALSPECIFYMAP.put("2-4","0004");
        CALSPECIFYMAP.put("2-8","0005");
        CALSPECIFYMAP.put("2-16","0006");

        CALSPECIFYMAP.put("2-32","0007");
        CALSPECIFYMAP.put("4-8","0008");
        CALSPECIFYMAP.put("4-16","0009");
        CALSPECIFYMAP.put("4-32","0010");
        CALSPECIFYMAP.put("4-64","0011");
        CALSPECIFYMAP.put("4-128","0012");

        CALSPECIFYMAP.put("8-16","0013");
        CALSPECIFYMAP.put("8-32","0014");
        CALSPECIFYMAP.put("8-64","0015");
        CALSPECIFYMAP.put("8-128","0016");
        CALSPECIFYMAP.put("16-32","0017");
        CALSPECIFYMAP.put("16-64","0018");

        CALSPECIFYMAP.put("16-128","0019");
        CALSPECIFYMAP.put("16-256","0020");
        CALSPECIFYMAP.put("32-64","0021");
        CALSPECIFYMAP.put("32-128","0022");
        CALSPECIFYMAP.put("32-256","0023");
        CALSPECIFYMAP.put("32-512","0024");

    }

    static {
        CPUMEMOERYMAP = new HashMap<String,String>();
        CPUMEMOERYMAP.put("0001","1-2");
        CPUMEMOERYMAP.put("0002","1-4");
        CPUMEMOERYMAP.put("0003","1-8");
        CPUMEMOERYMAP.put("0004","2-4");
        CPUMEMOERYMAP.put("0005","2-8");
        CPUMEMOERYMAP.put("0006","2-16");

        CPUMEMOERYMAP.put("0007","2-32");
        CPUMEMOERYMAP.put("0008","4-8");
        CPUMEMOERYMAP.put("0009","4-16");
        CPUMEMOERYMAP.put("0010","4-32");
        CPUMEMOERYMAP.put("0011","4-64");
        CPUMEMOERYMAP.put("0012","4-128");

        CPUMEMOERYMAP.put("0013","8-16");
        CPUMEMOERYMAP.put("0014","8-32");
        CPUMEMOERYMAP.put("0015","8-64");
        CPUMEMOERYMAP.put("0016","8-128");
        CPUMEMOERYMAP.put("0017","16-32");
        CPUMEMOERYMAP.put("0018","16-64");

        CPUMEMOERYMAP.put("0019","16-128");
        CPUMEMOERYMAP.put("0020","16-256");
        CPUMEMOERYMAP.put("0021","32-64");
        CPUMEMOERYMAP.put("0022","32-128");
        CPUMEMOERYMAP.put("0023","32-256");
        CPUMEMOERYMAP.put("0024","32-512");

    }

    static{
        G2Q_SERVICETYPEMAP = new HashMap<String,String>();
        G2Q_SERVICETYPEMAP.put("01001","appv-v71be1fi");
        G2Q_SERVICETYPEMAP.put("01002","appv-y0dunpcc");
        G2Q_SERVICETYPEMAP.put("01003","appv-fjjyg8ae");
        G2Q_SERVICETYPEMAP.put("01004","appv-e97gz6wz");
        G2Q_SERVICETYPEMAP.put("01006","appv-dy6k7pd0");
        G2Q_SERVICETYPEMAP.put("02001","appv-v71be1fi");
        G2Q_SERVICETYPEMAP.put("02002","appv-wvbdsw6a");
        G2Q_SERVICETYPEMAP.put("02003","appv-v71be1fi");
    }

    static{
        Q2G_SERVICETYPEMAP = new HashMap<String,String>();
        Q2G_SERVICETYPEMAP.put("appv-y0dunpcc","01002");
        Q2G_SERVICETYPEMAP.put("appv-fjjyg8ae","01003");
        Q2G_SERVICETYPEMAP.put("appv-e97gz6wz","01004");
        Q2G_SERVICETYPEMAP.put("appv-dy6k7pd0","01006");
        Q2G_SERVICETYPEMAP.put("appv-wvbdsw6a","02002");

    }

    static{
        SERVICETYPE_MAINROLE_MAP = new HashMap<String,String>();
        SERVICETYPE_MAINROLE_MAP.put("01001","slave");
        SERVICETYPE_MAINROLE_MAP.put("01002","hbase-slave");
        SERVICETYPE_MAINROLE_MAP.put("01003","");
        SERVICETYPE_MAINROLE_MAP.put("01004","");
        SERVICETYPE_MAINROLE_MAP.put("01006","es_master_node");
        SERVICETYPE_MAINROLE_MAP.put("02001","slave");
        SERVICETYPE_MAINROLE_MAP.put("02002","slave");
        SERVICETYPE_MAINROLE_MAP.put("02003","slave");


    }

}

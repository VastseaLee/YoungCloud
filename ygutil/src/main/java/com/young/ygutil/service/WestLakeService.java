package com.young.ygutil.service;

import com.young.ygutil.dao.WestLakeDao;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("westLakeService")
public class WestLakeService {

    @Resource
    private WestLakeDao westLakeDao;

    private Map<String, String> reMap = new HashMap<>() {{
        put("330106202000", "双浦镇");
        put("330100000000", "杭州市");
        put("330106000000", "西湖区");
        put("330106104000", "转塘街道");
    }};

    private Map<String, String> wuziMap = new HashMap<>() {{
        put("1", "救生器材");
        put("2", "小型抢险机具");
        put("3", "抢险物料");
        put("4", "备用备件");
    }};

    private Map<String, Integer> indexMap = new HashMap<>() {{
        put("水库", 1);
        put("水闸", 1);
        put("海塘", 1);
        put("山塘", 1);
        put("泵站", 1);
    }};


    public void init() {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet shuiku = wb.createSheet("水库");
        String[] heads = new String[]{
                "工程代码", "水库名称", "所在地区", "所在河流",
                "经度", "纬度", "水库类型", "工程等别",
                "工程建设情况", "完工时间（非加固）", "是否竣工验收（非加固）", "竣工验收时间（非加固）",
                "高程体系", "水库类别", "所在村", "溃决影响的乡镇",
                "是否有闸门", "是否有备用电源", "控制流域面积(k㎡)", "多年平均径流量（m³/s）",
                "设计洪水时最大泄量（m³/s）", "校核洪水位时最大泄量（m³/s）", "水库调节特性", "设计洪水标准(年)",
                "设计洪水位(m)", "校核洪水位(m)", "正常蓄水位(m)", "死水位(m)",
                "正常蓄水位相应水位面积( k㎡)", "总库容（万m³）", "兴利库容（万m³）", "调洪库容（万m³）",
                "调节库容（万m³）", "死库容（万m³）", "当前完好监测设备数量", "主要功能",
                "设计灌溉面积(万亩)", "实际灌溉面积(万亩)", "重要保护对象", "安全监测设施数量",
                "汛期限制水位开始日期", "汛期限制水位结束日期", "防洪高水位相应洪水标准", "汛期限制水位(m)",
                "防洪库容（万m³）", "防洪高水位(m)", "历史最高水位发生日期", "历史最高水位(m)",
                "历史最大入库流量发生日期", "历史最大入库流量（万m³）", "历史最大出库流量发生日期", "历史最大出库流量（万m³）",
                "汛期限制水位的相应库容（万m³）", "工程代码", "大坝类型按结构分", "主要挡水建筑物级别",
                "副坝座数", "大坝类型按材料分", "最大坝高(m)", "坝长(m)",
                "坝顶宽度(m)", "坝顶长度(m)", "坝顶高程(m)", "坝体防渗形式",
                "坝基地址", "坝基防渗措施", "坝址以上集雨面积(k㎡)", "泄水建筑物类型",
                "泄水建筑物级别", "正常/非常溢洪道", "最大泄洪流量", "建筑物位置",
                "孔数（条数）", "堰顶高程(m)", "堰顶净宽(m)", "放水设施形式",
                "主要泄洪建筑物型式", "遥测站名称", "测验项目", "通信方式",
                "防洪", "库容", "主管部门负责人名称", "主管部门负责人名称职务",
                "主管部门负责人名称手机号码", "政府分管负责人名称", "政府分管负责人职务", "政府总负责人名称",
                "政府总负责人职务", "地理对象类型", "坐标点集合", "图表图纸",
                "物资名称", "物资类型", "数量", "单位",
                "物资负责人", "保质期", "相关图片"

        };
        initSheet(shuiku, heads);

        heads = new String[]{
                "工程代码", "工程名称", "所在地区", "所在河流",
                "经度", "纬度", "山塘类型", "工程等别",
                "工程建设情况", "完工时间（非加固）", "高程系统", "山塘溃泱影响乡镇",
                "工程类型", "所在村", "工程代码", "放水设施形式",
                "主流长度", "多年平均年降雨量", "设计洪峰流量", "校核洪水位",
                "设计洪水位", "正常蓄水位", "死水位", "总容积",
                "正常容积", "保护人口", "灌溉面积", "供水规模",
                "供水人口", "受益村庄", "坝型", "坝顶高程",
                "防浪墙顶高程", "最大坝高", "坝顶长度", "泄洪建筑物坝顶宽度",
                "泄洪建筑物类型", "泄洪建筑物堰顶高程", "泄洪建筑物溢流宽度", "泄洪建筑物设计泄洪流量",
                "泄洪建筑物校核泄洪流量", "输水建筑物型式", "输水建筑物设计流量", "输水建筑物长度",
                "输水建筑物断面尺寸", "输水建筑物闸门尺寸", "工程任务", "校核标准",
                "设计标准", "坝顶材质", "校核洪峰流量（立方米每秒）", "坝顶宽度",
                "闸门型式", "大坝类型", "泄洪建筑物型式", "工程代码",
                "主管部门负责人名称", "主管部门负责人名称职务", "主管部门负责人名称手机号码", "政府分管负责人名称",
                "政府分管负责人职务", "政府总负责人名称", "政府总负责人职务", "地理对象类型",
                "坐标点集合", "图表图纸"
        };
        XSSFSheet shantang = wb.createSheet("山塘");
        initSheet(shantang, heads);

        heads = new String[]{
                "工程代码", "工程名称", "海塘级别", "海塘形式",
                "所在地区", "所在村", "开工日期", "完工日期",
                "是否竣工验收（非加固）", "竣工验收时间（非加固）", "海塘平均高程(米)", "保护面积（平方公里）",
                "设计潮位", "达到规划防潮标准的长度(m)", "设计防潮标准(年)", "海塘长度",
                "代表潮位站代码", "参照潮位站名称", "参照潮位站蓝色警戒潮位(m)", "参照潮位站黄色警戒潮位(m)",
                "参照潮位站橙色警戒潮位(m)", "参照潮位站红色警戒潮位(m)", "堤角防冲结构(基础)", "实际防潮标准(年)",
                "保护人口(万人)", "保护耕地(万公顷)", "水闸数量(孔)", "水闸型式",
                "起点位置", "起点桩号", "海堤(段)起点经度", "海堤(段)起点纬度",
                "终点位置", "终点桩号", "海堤(段)终点经度", "海堤(段)终点纬度",
                "起点堤顶高程(m)", "终点堤顶高程(m)", "保护重点设施", "泵站数量(处)",
                "工程代码", "主管部门负责人名称", "主管部门负责人名称职务", "主管部门负责人名称手机号码",
                "政府分管负责人名称", "政府分管负责人职务", "政府总负责人名称", "政府总负责人职务",
                "地理对象类型", "坐标点集合", "物资名称", "物资类型",
                "数量", "单位", "物资负责人", "保质期",
                "相关图片"
        };
        XSSFSheet haitang = wb.createSheet("海塘");
        initSheet(haitang, heads);

        heads = new String[]{
                "水闸名称", "水闸类型", "工程规模", "工程等别",
                "所在河流编码", "高程系统", "所在地区", "是否为套闸工程",
                "工程代码", "所在河流", "所在灌区", "经度",
                "纬度", "竣工验收时间", "是否为闸站工程", "闸上是否能通车",
                "所属水闸", "所在村", "建成时间", "是否竣工验收",
                "河道等级", "流域名称", "所在河流岸别", "是否有备用电源",
                "工程代码", "闸孔数量", "闸孔总净宽(m)", "副闸闸孔数量",
                "副闸闸孔总净宽(m)", "设计闸上水位(m)", "梅汛期内河控制水位(m)", "台汛期内河控制水位(m)",
                "设计排涝标准(年)", "设计灌溉面积(公顷)", "设计排涝面积(公顷)", "实际排涝面积(公顷)",
                "实际灌溉面积(公顷)", "正常蓄水位(m)", "水闸参照水位站名称", "水闸参照水位站警戒水位(m)",
                "水闸参照水位站站号", "水闸参照水位站危机水位(m)", "闸门型式", "水闸门尺寸（宽*高）(m*m)",
                "启闭机型式", "最大过闸流量(m³/s)", "闸底高程(m)", "上下游最大水位差(m)",
                "校核闸上水位(m)", "挡潮闸过闸流量", "挡潮闸设计潮水标准", "挡潮闸校核潮水标准",
                "工程代码", "主管部门负责人名称", "主管部门负责人名称职务", "主管部门负责人名称手机号码",
                "政府分管负责人名称", "政府分管负责人职务", "政府总负责人名称", "政府总负责人职务",
                "地理对象类型", "坐标点集合", "图表图纸", "物资名称",
                "物资类型", "数量", "单位", "物资负责人",
                "保质期", "相关图片"
        };
        XSSFSheet shuizha = wb.createSheet("水闸");
        initSheet(shuizha, heads);

        heads = new String[]{
                "工程代码", "泵站名称", "工程等别", "所在乡镇",
                "地理坐标（北纬）", "地理坐标（东经）", "所在河流（湖、库、渠、海堤）编码", "所在河流（湖、库、渠、海堤）名称",
                "工程任务", "所在流域编码", "泵站参照水位站警戒水位(m)", "泵站参照水位站危机水位(m)",
                "泵站参照水位站名称", "是否为闸站工程", "是否竣工验收（非加固）", "竣工验收时间（非加固）",
                "开工时间", "完工时间", "工程所在村名称", "泵站参照水位站编码",
                "泵站类型", "外江多年平均低水(潮)位（m)", "外江多年平均高水(潮)位（m)", "外江设计低水(潮)位（m)",
                "外江设计高水(潮)位（m)", "总平面布置图", "水泵台数", "工程等别",
                "主要建筑物级别", "典型横断面图", "单台设计流量(m³/s)", "水泵设计扬程",
                "电动机装机功率（kW）", "内河常水位", "流域面积", "电动机台数",
                "装机流量(m³/s)", "输电线路电压（kv)", "出水池排涝期最高运行水位(m)", "出水池排涝期设计运行水位(m)",
                "前池排涝期最低运行水位(m)", "前池排涝期最高运行水位(m)", "历史引水保证率（％）", "设计引水流量(m³/s)",
                "多年平均年引水流量(m³/s)", "年引水流量(m³/s)", "前池排涝期设计运行水位（m)", "图表图纸"
        };
        XSSFSheet bengzhan = wb.createSheet("泵站");
        initSheet(bengzhan, heads);

        //查出所有工程
        List<Map<String, Object>> mainList = westLakeDao.queryMain();

        Map<String, Integer> rowMap = new HashMap<>() {{
            put("水库", 1);
            put("山塘", 1);
            put("海塘", 1);
            put("水闸", 1);
            put("泵站", 1);
        }};

        mainList.forEach(map -> {
            String type = (String) map.get("type");
            switch (type) {
                case "山塘":
                    disposeShantang(shantang, map, rowMap);
                    break;
                case "海塘":
                    disposeHaitang(haitang, map, rowMap);
                    break;
                case "水闸":
                    disposeShuizha(shuizha, map, rowMap);
                    break;
                case "泵站":
                    disposeBengzha(bengzhan, map, rowMap);
                    break;
                default:
                    disposeShuiku(shuiku, map, rowMap);
            }
        });

        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\A\\Desktop\\westlake.xlsx")) {
            wb.write(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void disposeBengzha(XSSFSheet sheet, Map<String, Object> map, Map<String, Integer> rowMap) {
        int index = rowMap.get("泵站");
        int start = index;
        int col = 0;
        XSSFRow row = sheet.createRow(index++);
        String code = f(map, "code");
        XSSFCell cell = row.createCell(col++);
        cell.setCellValue(getIndex("泵站"));

        cell = row.createCell(col++);
        cell.setCellValue(code);
        ff(row, col++, map, "name");

        Map<String, Object> data = westLakeDao.query(code, "bus_pumpingbaseinfo");
        ff(row, col++, data, "type");
        cell = row.createCell(col++);
        cell.setCellValue(reMap.get(f(map, "region_code")));

        ff(row, col++, data, "longitude");
        ff(row, col++, data, "latitude");
        ff(row, col++, data, "riversNo");
        ff(row, col++, data, "riversName");
        ff(row, col++, data, "engineeringTask");
        ff(row, col++, data, "basinNo");
        ff(row, col++, data, "pumping_rf_wl_warnlevel");
        ff(row, col++, data, "pumping_rf_wl_wglevel");
        ff(row, col++, data, "pumping_rf_wl_name");
        ff(row, col++, data, "isSluiceStation");
        ff(row, col++, data, "isComplete");
        ff(row, col++, data, "finishTime");
        ff(row, col++, data, "startTime");
        ff(row, col++, data, "endTime");
        ff(row, col++, data, "village");
        ff(row, col++, data, "pumping_rf_wl_code");
        ff(row, col++, data, "pumpType");

        data = westLakeDao.query(code, "bus_pumpdetailinfo");
        ff(row, col++, data, "or_avg_min");
        ff(row, col++, data, "or_avg_max");
        ff(row, col++, data, "or_ds_min");
        ff(row, col++, data, "or_ds_max");
        ff(row, col++, data, "total_floorplan");
        ff(row, col++, data, "pumpNum");
        ff(row, col++, data, "category");
        ff(row, col++, data, "main_bl");
        ff(row, col++, data, "crossSection_View");
        ff(row, col++, data, "pump_Design_Flow");
        ff(row, col++, data, "pump_Design_Head");
        ff(row, col++, data, "Motor_Power");
        ff(row, col++, data, "inland_wl");
        ff(row, col++, data, "basin_area");
        ff(row, col++, data, "motorNum");
        ff(row, col++, data, "installed_Flow");
        ff(row, col++, data, "voltage");
        ff(row, col++, data, "op_maxi");
        ff(row, col++, data, "op_ds_maxi");
        ff(row, col++, data, "bp_min");
        ff(row, col++, data, "bp_max");
        ff(row, col++, data, "his_rate_guarantee");
        ff(row, col++, data, "ds_flowRate");
        ff(row, col++, data, "avg_year_flowRate");
        ff(row, col++, data, "year_flowRate");
        ff(row, col++, data, "bp_ds_max");

        data = westLakeDao.query(code, "bus_reservoir_safety");
        ff(row, col++, data, "code");
        ff(row, col++, data, "dname");
        ff(row, col++, data, "dpost");
        ff(row, col++, data, "dphone");
        ff(row, col++, data, "gfname");
        ff(row, col++, data, "gfpost");
        ff(row, col++, data, "gzname");
        ff(row, col++, data, "gzpost");

//        data = westLakeDao.query(code, "bus_reservoir_capacity_curve");
//        if(data != null){
//            ff(row, col++, data, "url");
//        }

        rowMap.put("泵站", index);
    }

    private void disposeShuizha(XSSFSheet sheet, Map<String, Object> map, Map<String, Integer> rowMap) {
        int index = rowMap.get("水闸");
        int start = index;
        int col = 0;
        XSSFRow row = sheet.createRow(index++);
        String code = f(map, "code");
        XSSFCell cell = row.createCell(col++);
        cell.setCellValue(getIndex("水闸"));


        ff(row, col++, map, "name");
        Map<String, Object> data = westLakeDao.query(code, "bus_slubase_info");
        ff(row, col++, data, "type");
        ff(row, col++, data, "engineeringScale");
        ff(row, col++, data, "engineeringLevel");
        ff(row, col++, data, "");
        ff(row, col++, data, "elevationSystem");

        cell = row.createCell(col++);
        cell.setCellValue(reMap.get(f(map, "region_code")));

        ff(row, col++, data, "isBuildingLevel");

        cell = row.createCell(col++);
        cell.setCellValue(code);

        ff(row, col++, data, "rivers");
        ff(row, col++, data, "allIrrigation");
        ff(row, col++, data, "longitude");
        ff(row, col++, data, "latitude");
        ff(row, col++, data, "buildedDate");
        ff(row, col++, data, "isSluiceWork");
        ff(row, col++, data, "isTraffic");
        ff(row, col++, data, "isriversluice");
        ff(row, col++, data, "villagee");
        ff(row, col++, data, "buildingtime");
        ff(row, col++, data, "isBuilded");
        ff(row, col++, data, "riverlevel");
        ff(row, col++, data, "basinname");
        ff(row, col++, data, "bankSide");
        ff(row, col++, data, "isHavePower");

        data = westLakeDao.query(code, "bus_sludetailed_info");
        ff(row, col++, data, "code");
        ff(row, col++, data, "gp_Number");
        ff(row, col++, data, "gp_t_Width");
        ff(row, col++, data, "vice_Gp_Number");
        ff(row, col++, data, "vice_Gp_t_Width");
        ff(row, col++, data, "design_s_waterlevel");
        ff(row, col++, data, "p_f_c_waterlevel");
        ff(row, col++, data, "t_f_c_waterlevel");
        ff(row, col++, data, "ds_drainage_standard");
        ff(row, col++, data, "ds_s_area");
        ff(row, col++, data, "ds_drainage_area");
        ff(row, col++, data, "at_drainage_area");
        ff(row, col++, data, "at_s_area");
        ff(row, col++, data, "normal_Impoundage_Level");
        ff(row, col++, data, "wg_rf_wl_name");
        ff(row, col++, data, "wg_rf_wl_warnlevel");
        ff(row, col++, data, "wg_rf_wl_stano");
        ff(row, col++, data, "wg_rf_wl_wglevel");
        ff(row, col++, data, "gate_Type");
        ff(row, col++, data, "gate_Size");
        ff(row, col++, data, "hoist_Type");
        ff(row, col++, data, "max_Flow");
        ff(row, col++, data, "sluice_Floor_Elevation");
        ff(row, col++, data, "biggest_Water_Level_Difference");
        ff(row, col++, data, "check_Sluice_Upstream_Level");
        ff(row, col++, data, "tide_flowRate");
        ff(row, col++, data, "tide_ds_FloodStandard");
        ff(row, col++, data, "tide_checkFloodStandard");

        data = westLakeDao.query(code, "bus_reservoir_safety");
        ff(row, col++, data, "code");
        ff(row, col++, data, "dname");
        ff(row, col++, data, "dpost");
        ff(row, col++, data, "dphone");
        ff(row, col++, data, "gfname");
        ff(row, col++, data, "gfpost");
        ff(row, col++, data, "gzname");
        ff(row, col++, data, "gzpost");

        List<Map<String, Object>> list = westLakeDao.list(code, "bus_project_path");
        int first = start;
        if (!CollectionUtils.isEmpty(list)) {
            boolean flag = false;
            for (Map<String, Object> map1 : list) {
                XSSFRow rr;
                int left = col;
                if (flag) {
                    rr = sheet.createRow(first);
                } else {
                    rr = row;
                }

                ff(rr, left++, map1, "type");
                ff(rr, left++, map1, "path");

                first++;
                flag = true;
            }
        }
        col += 2;

        data = westLakeDao.query(code, "bus_reservoir_capacity_curve");
        ff(row, col++, data, "url");

        data = westLakeDao.query(code, "bus_pre_materials");
        ff(row, col++, data, "name");
        cell = row.createCell(col++);
        cell.setCellValue(wuziMap.get(f(data, "type")));
        ff(row, col++, data, "num");
        ff(row, col++, data, "company");
        ff(row, col++, data, "managerId");
        ff(row, col++, data, "shelflife");
        ff(row, col++, data, "imgurl");

        rowMap.put("水闸", maxNum(index, first));
    }

    private void disposeHaitang(XSSFSheet sheet, Map<String, Object> map, Map<String, Integer> rowMap) {
        int index = rowMap.get("海塘");
        int start = index;
        int col = 0;
        XSSFRow row = sheet.createRow(index++);
        String code = f(map, "code");
        XSSFCell cell = row.createCell(col++);
        cell.setCellValue(getIndex("海塘"));

        cell = row.createCell(col++);
        cell.setCellValue(code);

        ff(row, col++, map, "name");

        Map<String, Object> data = westLakeDao.query(code, "bus_seawall");
        ff(row, col++, data, "seawallLevel");
        ff(row, col++, data, "seawallType");
        cell = row.createCell(col++);
        cell.setCellValue(reMap.get(f(map, "region_code")));
        ff(row, col++, data, "villagee");
        ff(row, col++, data, "startTime");
        ff(row, col++, data, "endTime");
        ff(row, col++, data, "isfinish");
        ff(row, col++, data, "finishTime");
        ff(row, col++, data, "aveElevation");
        ff(row, col++, data, "protectionArea");

        data = westLakeDao.query(code, "bus_seawall_feature");
        ff(row, col++, data, "designedLevel");
        ff(row, col++, data, "planningLength");
        ff(row, col++, data, "");
        ff(row, col++, data, "seawalllength");
        ff(row, col++, data, "stationCode");
        ff(row, col++, data, "stationName");
        ff(row, col++, data, "stationBlue");
        ff(row, col++, data, "stationYellow");
        ff(row, col++, data, "stationOrange");
        ff(row, col++, data, "stationRed");
        ff(row, col++, data, "footerStructure");
        ff(row, col++, data, "actualStandard");
        ff(row, col++, data, "protectedPopulation");
        ff(row, col++, data, "farmingArea");
        ff(row, col++, data, "damNum");
        ff(row, col++, data, "damType");
        ff(row, col++, data, "startPosition");
        ff(row, col++, data, "startStack");
        ff(row, col++, data, "startLongitude");
        ff(row, col++, data, "startLatitude");
        ff(row, col++, data, "endPostion");
        ff(row, col++, data, "endStack");
        ff(row, col++, data, "endLongitude");
        ff(row, col++, data, "endLatitude");
        ff(row, col++, data, "startAltitude");
        ff(row, col++, data, "endAltitude");
        ff(row, col++, data, "keyFacility");
        ff(row, col++, data, "pumpNum");

        data = westLakeDao.query(code, "bus_reservoir_safety");
        ff(row, col++, data, "code");
        ff(row, col++, data, "dname");
        ff(row, col++, data, "dpost");
        ff(row, col++, data, "dphone");
        ff(row, col++, data, "gfname");
        ff(row, col++, data, "gfpost");
        ff(row, col++, data, "gzname");
        ff(row, col++, data, "gzpost");

        List<Map<String, Object>> list = westLakeDao.list(code, "bus_project_path");
        int first = start;
        if (!CollectionUtils.isEmpty(list)) {
            boolean flag = false;
            for (Map<String, Object> map1 : list) {
                XSSFRow rr;
                int left = col;
                if (flag) {
                    rr = sheet.createRow(first);
                } else {
                    rr = row;
                }

                ff(rr, left++, map1, "type");
//                ff(rr, left++, map1, "path");
                ff(rr, left++, map1, "");

                first++;
                flag = true;
            }
        }
        col += 2;

        data = westLakeDao.query(code, "bus_pre_materials");
        ff(row, col++, data, "name");
        cell = row.createCell(col++);
        cell.setCellValue(wuziMap.get(f(data, "type")));
        ff(row, col++, data, "num");
        ff(row, col++, data, "company");
        ff(row, col++, data, "managerId");
        ff(row, col++, data, "shelflife");
        ff(row, col++, data, "imgurl");

        rowMap.put("海塘", maxNum(index, first));
    }

    private void disposeShantang(XSSFSheet sheet, Map<String, Object> map, Map<String, Integer> rowMap) {
        int index = rowMap.get("山塘");
        int start = index;
        int col = 0;
        XSSFRow row = sheet.createRow(index++);
        String code = f(map, "code");
        XSSFCell cell = row.createCell(col++);
        cell.setCellValue(getIndex("山塘"));

        cell = row.createCell(col++);
        cell.setCellValue(code);

        ff(row, col++, map, "name");
        cell = row.createCell(col++);
        cell.setCellValue(reMap.get(f(map, "region_code")));

        Map<String, Object> data = westLakeDao.query(code, "bus_pool");
        ff(row, col++, data, "river");
        ff(row, col++, data, "longitude");
        ff(row, col++, data, "latitude");
        ff(row, col++, data, "type");
        ff(row, col++, data, "level");
        ff(row, col++, data, "construction");
        ff(row, col++, data, "time");
        ff(row, col++, data, "heightsystem");
        ff(row, col++, data, "poolcrashaffect");
        ff(row, col++, data, "projecttype");
        ff(row, col++, data, "village");

        data = westLakeDao.query(code, "bus_pool_feat");
        ff(row, col++, data, "code");
        ff(row, col++, data, "drainagestructure");
        ff(row, col++, data, "length");
        ff(row, col++, data, "rain_fall");
        ff(row, col++, data, "peak_flow");
        ff(row, col++, data, "check_flood");
        ff(row, col++, data, "design_flood");
        ff(row, col++, data, "normal_water");
        ff(row, col++, data, "dead_water");
        ff(row, col++, data, "total_volume");
        ff(row, col++, data, "normal_volume");
        ff(row, col++, data, "protect_popu");
        ff(row, col++, data, "irrigated_area");
        ff(row, col++, data, "water_supply");
        ff(row, col++, data, "ws_popu");
        ff(row, col++, data, "dam_type");
        ff(row, col++, data, "crest_elevation");
        ff(row, col++, data, "wave_elevation");
        ff(row, col++, data, "dam_height");
        ff(row, col++, data, "crest_length");
        ff(row, col++, data, "flood_width");
        ff(row, col++, data, "crest_length");
        ff(row, col++, data, "flood_type");
        ff(row, col++, data, "flood_crest");
        ff(row, col++, data, "flood_ofwidth");
        ff(row, col++, data, "flood_design");
        ff(row, col++, data, "flood_check");
        ff(row, col++, data, "transport_type");
        ff(row, col++, data, "transport_design");
        ff(row, col++, data, "transport_length");
        ff(row, col++, data, "transport_cross");
        ff(row, col++, data, "transport_gate");
        ff(row, col++, data, "project");
        ff(row, col++, data, "check_standard");
        ff(row, col++, data, "design_standard");
        ff(row, col++, data, "dam_material");
        ff(row, col++, data, "checkflow");
        ff(row, col++, data, "crestwidth");
        ff(row, col++, data, "gatetype");
        ff(row, col++, data, "dammaterials");
        ff(row, col++, data, "releasetype");

        data = westLakeDao.query(code, "bus_reservoir_safety");
        ff(row, col++, data, "code");
        ff(row, col++, data, "dname");
        ff(row, col++, data, "dpost");
        ff(row, col++, data, "dphone");
        ff(row, col++, data, "gfname");
        ff(row, col++, data, "gfpost");
        ff(row, col++, data, "gzname");
        ff(row, col++, data, "gzpost");

        data = westLakeDao.query(code, "bus_project_path");
        ff(row, col++, data, "type");
        ff(row, col++, data, "path");

        data = westLakeDao.query(code, "bus_reservoir_capacity_curve");
        ff(row, col++, data, "url");

        rowMap.put("山塘", index);
    }

    private int getIndex(String s) {
        return indexMap.put(s,indexMap.get(s)+1);
    }

    private void disposeShuiku(XSSFSheet sheet, Map<String, Object> map, Map<String, Integer> rowMap) {
        int index = rowMap.get("水库");
        int start = index;
        XSSFRow row = sheet.createRow(index++);
        String code = f(map, "code");
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(getIndex("水库"));

        cell = row.createCell(1);
        cell.setCellValue(code);

        cell = row.createCell(2);
        cell.setCellValue(f(map, "name"));

        cell = row.createCell(3);
        cell.setCellValue(reMap.get(f(map, "region_code")));
        int col = 17;
        Map<String, Object> reservoir = westLakeDao.queryReservoir(code);
        cell = row.createCell(4);
        cell.setCellValue(f(reservoir, "river"));

        cell = row.createCell(5);
        cell.setCellValue(f(reservoir, "longitude"));

        cell = row.createCell(6);
        cell.setCellValue(f(reservoir, "latitude"));

        cell = row.createCell(7);
        cell.setCellValue(f(reservoir, "type"));

        cell = row.createCell(8);
        cell.setCellValue(f(reservoir, "projectGrade"));

        cell = row.createCell(9);
        cell.setCellValue(f(reservoir, "projectConstruction"));

        cell = row.createCell(10);
        cell.setCellValue(f(reservoir, "constructionTime"));

        cell = row.createCell(11);
        cell.setCellValue(f(reservoir, "isfinish"));

        cell = row.createCell(12);
        cell.setCellValue(f(reservoir, "finishTime"));

        cell = row.createCell(13);
        cell.setCellValue(f(reservoir, "elevationSystem"));

        cell = row.createCell(14);
        cell.setCellValue(f(reservoir, "category"));

        cell = row.createCell(15);
        cell.setCellValue(f(reservoir, "village"));

        cell = row.createCell(16);
        cell.setCellValue(f(reservoir, "countryNumer"));
        ff(row, col++, reservoir, "isHaveGate");
        ff(row, col++, reservoir, "isHavePower");

        Map<String, Object> data = westLakeDao.query(code, "bus_reservoir_feature");
        ff(row, col++, data, "controlArea");
        ff(row, col++, data, "averageFlow");
        ff(row, col++, data, "degisnMaxFlood");
        ff(row, col++, data, "checkMaxFlood");
        ff(row, col++, data, "reservoirAjust");
        ff(row, col++, data, "designFloodStandard");
        ff(row, col++, data, "designFlood");
        ff(row, col++, data, "checkFlood");
        ff(row, col++, data, "normalStorageLevel");
        ff(row, col++, data, "deadWaterLevel");
        ff(row, col++, data, "normalWaterArea");
        ff(row, col++, data, "totalStorage");
        ff(row, col++, data, "goodStorage");
        ff(row, col++, data, "ajustFloodStorage");
        ff(row, col++, data, "ajustStorage");
        ff(row, col++, data, "deadtorage");
        ff(row, col++, data, "normalCount");
        ff(row, col++, data, "functions");
        ff(row, col++, data, "designArea");
        ff(row, col++, data, "actualArea");
        ff(row, col++, data, "protectObject");
        ff(row, col++, data, "facilityCount");

        ff(row, col++, data, "waterlimitstarttime");
        ff(row, col++, data, "waterlimitendtime");
        ff(row, col++, data, "highLevelStandard");
        ff(row, col++, data, "waterLimit");
        ff(row, col++, data, "floodStorage");
        ff(row, col++, data, "highLevel");
        ff(row, col++, data, "highestWaterLevelTime");
        ff(row, col++, data, "highestWaterLevel");
        ff(row, col++, data, "largestWaterInTime");
        ff(row, col++, data, "largestWaterIn");
        ff(row, col++, data, "largestWaterOutTime");
        ff(row, col++, data, "largestWaterOut");
        ff(row, col++, data, "waterLimitStorage");

        List<Map<String, Object>> list = westLakeDao.list(code, "bus_reservoir_flood_releasing_hole");
        int first = start;
        if (!CollectionUtils.isEmpty(list)) {
            boolean flag = false;
            for (Map<String, Object> map1 : list) {
                XSSFRow rr;
                int left = col;
                if (flag) {
                    rr = sheet.createRow(first);
                } else {
                    rr = row;
                }

                ff(rr, left++, map1, "code");
                ff(rr, left++, map1, "retainWaterConstructType");
                ff(rr, left++, map1, "retainWaterConstructLevel");
                ff(rr, left++, map1, "damCount");
                ff(rr, left++, map1, "mainDamnType");
                ff(rr, left++, map1, "damHighest");
                ff(rr, left++, map1, "damLong");
                ff(rr, left++, map1, "topWide");
                ff(rr, left++, map1, "damTopLong");
                ff(rr, left++, map1, "damTopElevation");
                ff(rr, left++, map1, "damSeepageType");
                ff(rr, left++, map1, "damFoundationAddress");
                ff(rr, left++, map1, "rainArea");

                first++;
                flag = true;
            }
        }

        col += 14;
        list = westLakeDao.list(code, "bus_reservoir_water_retaining_structure");
        int second = start;
        if (!CollectionUtils.isEmpty(list)) {
            boolean flag = false;
            for (Map<String, Object> map1 : list) {
                XSSFRow rr;
                int left = col;
                if (flag) {
                    rr = sheet.createRow(second);
                } else {
                    rr = row;
                }

                ff(rr, left++, map1, "releasingStructureType");
                ff(rr, left++, map1, "releasingStructureLevel");
                ff(rr, left++, map1, "spillWay");
                ff(rr, left++, map1, "maxReleasing");
                ff(rr, left++, map1, "buildingAddress");
                ff(rr, left++, map1, "holeNum");
                ff(rr, left++, map1, "crestElevation");
                ff(rr, left++, map1, "crestWidth");
                ff(rr, left++, map1, "drainagestructure");
                ff(rr, left++, map1, "waterstructure");

                second++;
                flag = true;
            }
        }
        col += 10;
        list = westLakeDao.list(code, "bus_reservoir_observation_station");
        int three = start;
        if (!CollectionUtils.isEmpty(list)) {
            boolean flag = false;
            for (Map<String, Object> map1 : list) {
                XSSFRow rr;
                int left = col;
                if (flag) {
                    rr = sheet.createRow(three);
                } else {
                    rr = row;
                }

                ff(rr, left++, map1, "stationName");
                ff(rr, left++, map1, "testingProject");
                ff(rr, left++, map1, "communicationMode");

                three++;
                flag = true;
            }
        }
        col += 3;

        list = westLakeDao.list(code, "bus_reservoir_capacity_curve");
        int four = start;
        if (!CollectionUtils.isEmpty(list)) {
            boolean flag = false;
            for (Map<String, Object> map1 : list) {
                XSSFRow rr;
                int left = col;
                if (flag) {
                    rr = sheet.createRow(four);
                } else {
                    rr = row;
                }

                ff(rr, left++, map1, "waterLevel");
                ff(rr, left++, map1, "storage");

                four++;
                flag = true;
            }
        }
        col += 2;

        data = westLakeDao.query(code, "bus_reservoir_safety");
        ff(row, col++, data, "dname");
        ff(row, col++, data, "dpost");
        ff(row, col++, data, "dphone");
        ff(row, col++, data, "gfname");
        ff(row, col++, data, "gfpost");
        ff(row, col++, data, "gzname");
        ff(row, col++, data, "gzpost");

        data = westLakeDao.query(code, "bus_project_path");
        ff(row, col++, data, "type");
        ff(row, col++, data, "path");

        list = westLakeDao.list(code, "bus_reservoir_capacity_curve");
        int five = start;
        if (!CollectionUtils.isEmpty(list)) {
            boolean flag = false;
            for (Map<String, Object> map1 : list) {
                XSSFRow rr;
                int left = col;
                if (flag) {
                    rr = sheet.createRow(five);
                } else {
                    rr = row;
                }

                ff(rr, left++, map1, "url");

                five++;
                flag = true;
            }
        }
        col++;

        list = westLakeDao.list(code, "bus_pre_materials");
        int six = start;
        if (!CollectionUtils.isEmpty(list)) {
            boolean flag = false;
            for (Map<String, Object> map1 : list) {
                XSSFRow rr;
                int left = col;
                if (flag) {
                    rr = sheet.createRow(six);
                } else {
                    rr = row;
                }

                ff(rr, left++, map1, "name");

                cell = rr.createCell(left++);
                cell.setCellValue(wuziMap.get(f(map1, "type")));

                ff(rr, left++, map1, "num");
                ff(rr, left++, map1, "company");
                ff(rr, left++, map1, "managerId");
                ff(rr, left++, map1, "shelflife");
                ff(rr, left++, map1, "imgurl");

                six++;
                flag = true;
            }
        }
        col++;

        rowMap.put("水库", maxNum(index, first, second, three, four, five, six));
    }

    private int maxNum(int... nums) {
        int max = 0;
        for (int num : nums) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    private void ff(XSSFRow row, int i, Map<String, Object> map, String s) {
        XSSFCell cell = row.createCell(i);
        cell.setCellValue(f(map, s));
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String f(Map<String, Object> map, String key) {
        if(map == null){
            return "";
        }
        Object o = map.get(key);
        if (o != null) {
            if (o instanceof Date) {
                return sdf.format(o);
            }
            return String.valueOf(o);
        }
        return "";
    }

    private void initSheet(Sheet sheet, String[] heads) {
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("序号");
        for (int i = 1; i < heads.length + 1; i++) {
            cell = row.createCell(i);
            cell.setCellValue(heads[i - 1]);
        }
    }
}

package com.young.other.util;

import com.young.other.bean.YoungUser;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {


    public static void f(String[][] heads, String[] fields, List<?> dataList, String tableName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        int rowNum = 0;
        if (StringUtils.isNotEmpty(tableName)) {
            CellRangeAddress region = new CellRangeAddress(0, 0, 0, heads[0].length);
            rowNum++;
        }
        Row row;
        Cell cell;
        //组装Excel头
        String[] first = heads[0];
        String[] second = heads[1];
        int len = first.length;
        Row firstRow = sheet.createRow(rowNum);
        Row secondRow = sheet.createRow(rowNum + 1);
        for (int i = 0; i < len; i++) {
            if (StringUtils.isNotEmpty(first[i]) && StringUtils.isEmpty(second[i])) {
                cell = firstRow.createCell(i);
                cell.setCellValue(first[i]);
                cell.setCellStyle(style);
                CellRangeAddress region = new CellRangeAddress(rowNum, rowNum + 1, i, i);
                sheet.addMergedRegion(region);
            } else {
                int left = i;
                while (++i < len && StringUtils.isEmpty(first[i])) ;
                cell = firstRow.createCell(left);
                cell.setCellValue(first[left]);
                cell.setCellStyle(style);
                CellRangeAddress region = new CellRangeAddress(rowNum, rowNum, left, --i);
                sheet.addMergedRegion(region);
                for (int j = left; j <= i; j++) {
                    cell = secondRow.createCell(j);
                    cell.setCellValue(second[j]);
                    cell.setCellStyle(style);
                }
            }
        }



        try {
            OutputStream os = new FileOutputStream("C:\\Users\\A\\Desktop\\test1.xlsx");
            workbook.write(os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<YoungUser> list = new ArrayList<>() {{
            add(new YoungUser("Lee", 1, "杭州"));
            add(new YoungUser("Wang", 2, "温州"));
            add(new YoungUser("Young", 3, "上海"));
        }};
        String[][] heads = new String[][]{
                {"设区市", "县市区", "工程名称", "工程规模","最大坝高（m）", "蓄水验收时间（年）", "完工时间（年）", "竣工时间（年）","最近一次安全鉴定", "", "", "", "加固计划", "", "最近一次除险加固情况"},
                {"", "", "", "",null,null,null,null,"最近一次安全鉴定", "", "", "", "加固计划", "", "最近一次除险加固情况"}
        };
        f(heads, null, list, null);
    }

    public static <T> void fs(List<T> list) throws Exception {
        for (T t : list) {
            Method method = t.getClass().getMethod("getName");
            Object name = method.invoke(t);
            System.out.println(name);
        }
    }
}

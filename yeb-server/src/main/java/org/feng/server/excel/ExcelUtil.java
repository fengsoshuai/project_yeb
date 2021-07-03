package org.feng.server.excel;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.poi.ss.usermodel.Workbook;
import org.feng.consts.Consts;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

/**
 * 表格工具类
 * @author FengJinSong
 */
public class ExcelUtil {

    /**
     * 生成Excel的名字，生成规则是 simpleName_当前毫秒数
     * @param simpleName 简写文件名
     * @return 最终的文件名（不带后缀）
     */
    private static String generateFileName(String simpleName){
        return String.join(Consts.UNDERSCORE, simpleName, Long.toString(System.currentTimeMillis()));
    }

    /**
     * 导出Excel文件
     * @param response 响应对象
     * @param simpleName 简称（文件名）
     * @param clazz 实体类型
     * @param data 数据
     * @param <T> 泛型
     */
    public static <T> void export(HttpServletResponse response, String simpleName, Class<T> clazz, List<T> data){
        String fileName = generateFileName(simpleName);
        String fullName = String.join(Consts.POINT, fileName, Consts.XLS);
        ExportParams params = new ExportParams(fileName, simpleName, ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, clazz, data);

        ServletOutputStream outputStream = null;
        try {
            // 响应流
            response.setHeader(Consts.CONTENT_TYPE, "application/octet-stream");
            // 防止中文乱码
            response.setHeader(Consts.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(fullName, Consts.UTF_8));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(Objects.nonNull(outputStream)){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

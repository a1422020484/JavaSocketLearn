package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.util.ToStringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 把excel转成java模版文件
 *
 * @author guoqiong.shi
 */
public class ParseExcelToModelFile {
    /**
     * 模版属性名
     */
    private static List<String> modelName = new ArrayList<>();
    /**
     * 模版类型
     */
    private static List<String> modelType = new ArrayList<>();
    /**
     * 模版注释
     */
    private static List<String> modelAnnotation = new ArrayList<>();
    /**
     * 后端是否读取
     */
    private static List<Integer> isRead = new ArrayList<>();

    /**
     * class名字
     */
    private static String modelClassName;

    /**
     * 读取的列数
     */
    private static final int READ_ROW = 4;

    /**
     * src\\main\\java\\
     */
    private static final String strPath = "src\\main\\java\\";
    /**
     * \\
     */
    private static final String str1 = "\\";
    /**
     * \t
     */
    private static final String str2 = "\t";
    /**
     * \n
     */
    private static final String str3 = "\n";

    /**
     * 工作空间路径
     */
    private static String resourcePath = System.getProperty("user.dir");

    /**
     * 读取xlsx文件,支持2007以上版本(按工作表所在位置)
     *
     * @param is
     * @param index 工作表的索引，从0开始
     */
    public static void readXlsx(InputStream is, int index) {
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (xssfWorkbook != null) {
            int sheetNum = xssfWorkbook.getNumberOfSheets();
            if (index > sheetNum) {
                System.out.println("输入的工作表索引超出长度");
                return;
            }
            // 获取工作表(一个excel包括多个工作表)
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(index);
            if (xssfSheet == null) {
                return;
            }
           // modelClassName = ToStringUtils.toUpperCase4Index(xssfSheet.getSheetName());
            int rowNum = xssfSheet.getLastRowNum();
            if (rowNum > READ_ROW) {
                rowNum = READ_ROW;
            }
            for (int i = 0; i <= rowNum; i++) {
                XSSFRow xssfRow = xssfSheet.getRow(i);
                if (xssfRow != null) {
                    int cellNum = xssfRow.getLastCellNum();
                    for (int j = 0; j < cellNum; j++) {
                        if (xssfRow.getCell(j) == null || xssfRow.getCell(j) == null || "".equals(xssfRow.getCell(j).toString())) {
                            continue;
                        }
                        if (i == 0) {
                            modelName.add(xssfRow.getCell(j).toString());
                        } else if (i == 1) {
                            modelType.add(xssfRow.getCell(j).toString());
                        } else if (i == 2) {
                            modelAnnotation.add(xssfRow.getCell(j).toString());
                        } else if (i == 3) {
                            isRead.add(new Float(Float.parseFloat(xssfRow.getCell(j).toString())).intValue());
                        }
                    }
                }
            }
        }
    }

    /**
     * 读取xlsx文件,支持2007以上版本（按工作表名读取）
     *
     * @param is
     * @param sheetName 工作表名
     */
    public static void readXlsx(InputStream is, String sheetName) {
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (xssfWorkbook != null) {
            int sheetNum = xssfWorkbook.getNumberOfSheets();
            for (int z = 0; z < sheetNum; z++) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(z);
                // 获取工作表(一个excel包括多个工作表)
                if (xssfSheet == null) {
                    return;
                }
                if (!xssfSheet.getSheetName().equalsIgnoreCase(sheetName)) {
                    continue;
                }
                //modelClassName = ToStringUtils.toUpperCase4Index(xssfSheet.getSheetName());
                int rowNum = xssfSheet.getLastRowNum();
                if (rowNum > READ_ROW) {
                    rowNum = READ_ROW;
                }
                for (int i = 0; i <= rowNum; i++) {
                    XSSFRow xssfRow = xssfSheet.getRow(i);
                    if (xssfRow != null) {
                        int cellNum = xssfRow.getLastCellNum();
                        for (int j = 0; j < cellNum; j++) {
                            if (xssfRow.getCell(j) == null || xssfRow.getCell(j) == null || "".equals(xssfRow.getCell(j).toString())) {
                                continue;
                            }
                            if (i == 0) {
                                modelName.add(xssfRow.getCell(j).toString());
                            } else if (i == 1) {
                                modelType.add(xssfRow.getCell(j).toString());
                            } else if (i == 2) {
                                modelAnnotation.add(xssfRow.getCell(j).toString());
                            } else if (i == 3) {
                                isRead.add(new Float(Float.parseFloat(xssfRow.getCell(j).toString())).intValue());
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 生成java文件
     *
     * @param packageNamePath 相同包路径下文件path
     * @throws Exception
     */
    public static void createModelJavaFile(String packageNamePath) throws Exception {
        if (modelClassName == null) {
            throw new Exception("modelClassName为NULL");
        }
        if (modelAnnotation.size() == 0 || modelName.size() == 0 || modelType.size() == 0) {
            throw new Exception("解析excel得到的size为0");
        }
        String packagePath = packageNamePath.replace(".", str1);
        File file = new File(resourcePath + str1 + strPath + packagePath + str1 + modelClassName + "Model" + ".java");
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("文件路径：" + file);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            throw new Exception("文件已存在:" + file);
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write("package" + str2 + packageNamePath + ";" + str3 + str3);
            writer.write("public" + str2 + "class" + str2 + modelClassName + "Model" + "{" + str3);
            int size = modelName.size();
            for (int i = 0; i < size; i++) {
                // 客户端字段屏蔽
                if (isRead.get(i) == 1) {
                    continue;
                }
                if (modelAnnotation.get(i) == null || "".equals(modelAnnotation.get(i))) {
                    continue;
                }
                // 生成注释
                writer.write(str2 + "/**" + str3);
                writer.write(str2 + "*" + modelAnnotation.get(i) + str3);
                writer.write(str2 + "*/" + str3);
                // 属性
                if (modelType.get(i).equals("string")) {
                    //writer.write(str2 + "private" + str2 + ToStringUtils.toUpperCase4Index(modelType.get(i)) + str2 + modelName.get(i) + ";" + str3);
                } else {
                    writer.write(str2 + "private" + str2 + modelType.get(i) + str2 + modelName.get(i) + ";" + str3);
                }
            }
            writer.write("}");
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }


    public static void readXml() {
        long lasting = System.currentTimeMillis();
        try {
            InputStream is = new FileInputStream("G:\\Users\\yang\\Desktop\\y英雄_auto.xml");
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            String s = doc.getDocType().getElementName();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public static void main(String[] args) throws Exception {
		readXml();
	}
}

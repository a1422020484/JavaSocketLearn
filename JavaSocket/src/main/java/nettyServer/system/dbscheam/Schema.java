package nettyServer.system.dbscheam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @author yangxp
 */
@Component
public class Schema implements ApplicationContextAware {

    public static final String DB_SCHEMA_FILE_NAME = "/GameServerDBSchema.json";

    public static List<Column> localColumns;
    public static List<Column> dbColumns;

//    private static SchemaDAO schemaDAO;
    private static String database;

    /**
     * 读取本地保存的列数据
     *
     * @throws Exception
     */
    static void loadLocal() {
        URL url = Schema.class.getResource(DB_SCHEMA_FILE_NAME);
        if (url == null) {
            return;
        }
//        String path = GameUtils.getPwd() + DB_SCHEMA_FILE_NAME;
        File file = new File(url.getPath());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            String content = IOUtils.toString(fis, "utf-8");
            if (StringUtils.isNotBlank(content)) {
                localColumns = JSON.parseObject(content, new TypeReference<List<Column>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void loadDBSchema() {
        try {
//            database = schemaDAO.selectDatabase();
//            dbColumns = schemaDAO.getColumns(database);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void verifySchema() {
        loadLocal();

        loadDBSchema();

        if (localColumns == null) {
            return; // 本地为空,不验证
        }

        if (dbColumns == null) {
            throw new Error("读取`" + database + "`的表结构列表为空!");
        }

        compare();
    }

    static void compare() {
        Map<String, Column> left = new HashMap<>(2048); // localColumns
        Map<String, Column> right = new HashMap<>(2048); // dbColumns
        Set<String> nameKeys = new HashSet<>(2048); // tableName.columnName 唯一

        localColumns.forEach(column -> {
            String key = column.TABLE_NAME + "." + column.COLUMN_NAME;
            left.put(key, column);
            nameKeys.add(key);
        });
        dbColumns.forEach(column -> {
            String key = column.TABLE_NAME + "." + column.COLUMN_NAME;
            right.put(key, column);
            nameKeys.add(key);
        });

        System.out.println("正在验证数据库Schema,左:Local,右:Online,以' -- '分隔.");
        boolean hasErr = false;
        for (String key : nameKeys) {
            Column leftColumn = left.get(key);
            Column rightColumn = right.get(key);
            if (leftColumn == null || rightColumn == null) {
                System.out.println("列名对比: " + (leftColumn != null ? key : "缺失") + " -- " + (rightColumn != null ? key : "缺失"));
                hasErr = true;
            } else {
                String rs = leftColumn.compare(rightColumn);
                if (rs != null) {
                    System.out.println("列属性对比: " + key);
                    System.out.print(rs);
                    hasErr = true;
                }
            }
        }
        if (hasErr) {
            System.out.println("\n服务器启动失败::验证数据库Schema时发现错误!");
            System.exit(0);
        } else {
            System.out.println("验证数据库Schema结束,正常!");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        schemaDAO = applicationContext.getBean(SchemaDAO.class);
    }
}

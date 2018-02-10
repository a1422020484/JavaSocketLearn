package nettyServer.system.dbscheam;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;

/**
 * MYSQL 5.6
 * @author xiezuojie
 */
public class Column {
    String TABLE_NAME; // 表名
    String COLUMN_NAME; // 列名
    String ORDINAL_POSITION; // 排序
    String COLUMN_DEFAULT; // 默认值
    String IS_NULLABLE; // 是否可为NULL
    String DATA_TYPE; // 数据类型 int | text ...
    String COLUMN_KEY; // 键 PRI
    String EXTRA; // auto_increment
    String COLUMN_TYPE; // 列类型 varchar(255) | bigint(20) ...
    String COLUMN_COMMENT; // 列注释

    String CHARACTER_SET_NAME; // utf8mb4
    String COLLATION_NAME; // utf8mb4_bin

    public String compare(Column t) {
        StringBuilder builder = new StringBuilder(256);
        if (!StringUtils.equals(COLUMN_NAME, t.COLUMN_NAME)) {
            String fName = "COLUMN_NAME";
            builder.append("  -列名　 ").append(fieldValue(fName)).append(" -- ").append(t.fieldValue(fName)).append("\n");
        }
        if (!StringUtils.equals(COLUMN_DEFAULT, t.COLUMN_DEFAULT)) {
            String fName = "COLUMN_DEFAULT";
            builder.append("  -默认值 ").append(fieldValue(fName)).append(" -- ").append(t.fieldValue(fName)).append("\n");
        }
        if (!StringUtils.equals(IS_NULLABLE, t.IS_NULLABLE)) {
            String fName = "IS_NULLABLE";
            builder.append("  -可为空 ").append(fieldValue(fName)).append(" -- ").append(t.fieldValue(fName)).append("\n");
        }
        if (!StringUtils.equals(COLUMN_TYPE, t.COLUMN_TYPE)) {
            String fName = "COLUMN_TYPE";
            builder.append("  -列类型 ").append(fieldValue(fName)).append(" -- ").append(t.fieldValue(fName)).append("\n");
        }
        if (!StringUtils.equals(COLUMN_KEY, t.COLUMN_KEY)) {
            String fName = "COLUMN_KEY";
            builder.append("  -键　　 ").append(fieldValue(fName)).append(" -- ").append(t.fieldValue(fName)).append("\n");
        }
        if (!StringUtils.equals(EXTRA, t.EXTRA)) {
            String fName = "EXTRA";
            builder.append("  -额外　 ").append(fieldValue(fName)).append(" -- ").append(t.fieldValue(fName)).append("\n");
        }
        if (builder.length() == 0) {
            return null;
        }
        return builder.toString();
    }

    private String fieldValue(String fieldName) {
        Class<?> clazz = this.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (field != null) {
                Object value = field.get(this);
                if (value != null) {
                    return (String) value;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "缺失";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        if (!TABLE_NAME.equals(column.TABLE_NAME)) return false;
        if (!COLUMN_NAME.equals(column.COLUMN_NAME)) return false;
        if (COLUMN_DEFAULT != null ? !COLUMN_DEFAULT.equals(column.COLUMN_DEFAULT) : column.COLUMN_DEFAULT != null)
            return false;
        if (!IS_NULLABLE.equals(column.IS_NULLABLE)) return false;
        if (!DATA_TYPE.equals(column.DATA_TYPE)) return false;
        if (COLUMN_KEY != null ? !COLUMN_KEY.equals(column.COLUMN_KEY) : column.COLUMN_KEY != null) return false;
        if (EXTRA != null ? !EXTRA.equals(column.EXTRA) : column.EXTRA != null) return false;
        return COLUMN_TYPE.equals(column.COLUMN_TYPE);
    }

    @Override
    public int hashCode() {
        int result = TABLE_NAME.hashCode();
        result = 31 * result + COLUMN_NAME.hashCode();
        result = 31 * result + (COLUMN_DEFAULT != null ? COLUMN_DEFAULT.hashCode() : 0);
        result = 31 * result + IS_NULLABLE.hashCode();
        result = 31 * result + DATA_TYPE.hashCode();
        result = 31 * result + (COLUMN_KEY != null ? COLUMN_KEY.hashCode() : 0);
        result = 31 * result + (EXTRA != null ? EXTRA.hashCode() : 0);
        result = 31 * result + COLUMN_TYPE.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Column{" +
                "TABLE_NAME='" + TABLE_NAME + '\'' +
                ", COLUMN_NAME='" + COLUMN_NAME + '\'' +
                ", ORDINAL_POSITION='" + ORDINAL_POSITION + '\'' +
                ", COLUMN_DEFAULT='" + COLUMN_DEFAULT + '\'' +
                ", IS_NULLABLE='" + IS_NULLABLE + '\'' +
                ", DATA_TYPE='" + DATA_TYPE + '\'' +
                ", COLUMN_KEY='" + COLUMN_KEY + '\'' +
                ", EXTRA='" + EXTRA + '\'' +
                ", COLUMN_TYPE='" + COLUMN_TYPE + '\'' +
                ", COLUMN_COMMENT='" + COLUMN_COMMENT + '\'' +
                ", CHARACTER_SET_NAME='" + CHARACTER_SET_NAME + '\'' +
                ", COLLATION_NAME='" + COLLATION_NAME + '\'' +
                '}';
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public String getORDINAL_POSITION() {
        return ORDINAL_POSITION;
    }

    public void setORDINAL_POSITION(String ORDINAL_POSITION) {
        this.ORDINAL_POSITION = ORDINAL_POSITION;
    }

    public String getCOLUMN_DEFAULT() {
        return COLUMN_DEFAULT;
    }

    public void setCOLUMN_DEFAULT(String COLUMN_DEFAULT) {
        this.COLUMN_DEFAULT = COLUMN_DEFAULT;
    }

    public String getIS_NULLABLE() {
        return IS_NULLABLE;
    }

    public void setIS_NULLABLE(String IS_NULLABLE) {
        this.IS_NULLABLE = IS_NULLABLE;
    }

    public String getDATA_TYPE() {
        return DATA_TYPE;
    }

    public void setDATA_TYPE(String DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    public String getCOLUMN_KEY() {
        return COLUMN_KEY;
    }

    public void setCOLUMN_KEY(String COLUMN_KEY) {
        this.COLUMN_KEY = COLUMN_KEY;
    }

    public String getEXTRA() {
        return EXTRA;
    }

    public void setEXTRA(String EXTRA) {
        this.EXTRA = EXTRA;
    }

    public String getCOLUMN_TYPE() {
        return COLUMN_TYPE;
    }

    public void setCOLUMN_TYPE(String COLUMN_TYPE) {
        this.COLUMN_TYPE = COLUMN_TYPE;
    }

    public String getCOLUMN_COMMENT() {
        return COLUMN_COMMENT;
    }

    public void setCOLUMN_COMMENT(String COLUMN_COMMENT) {
        this.COLUMN_COMMENT = COLUMN_COMMENT;
    }

    public String getCHARACTER_SET_NAME() {
        return CHARACTER_SET_NAME;
    }

    public void setCHARACTER_SET_NAME(String CHARACTER_SET_NAME) {
        this.CHARACTER_SET_NAME = CHARACTER_SET_NAME;
    }

    public String getCOLLATION_NAME() {
        return COLLATION_NAME;
    }

    public void setCOLLATION_NAME(String COLLATION_NAME) {
        this.COLLATION_NAME = COLLATION_NAME;
    }
}

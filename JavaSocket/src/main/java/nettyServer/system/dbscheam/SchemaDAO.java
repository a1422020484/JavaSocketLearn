package nettyServer.system.dbscheam;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

/**
 * @author yangxp
 */
@DAO
public interface SchemaDAO {

    String TABLE = "information_schema.columns";
    String FIELDS = "TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION," +
            "COLUMN_DEFAULT,IS_NULLABLE,DATA_TYPE,COLUMN_KEY," +
            "EXTRA,COLUMN_TYPE,COLUMN_COMMENT,CHARACTER_SET_NAME," +
            "COLLATION_NAME";

    @SQL("SELECT " + FIELDS + " FROM information_schema.columns WHERE table_schema=:1")
    List<Column> getColumns(String database);

    @SQL("SELECT DATABASE()")
    String selectDatabase();
}

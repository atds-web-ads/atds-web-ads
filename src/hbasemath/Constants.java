package hbasemath;

import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

/**
 * Some constants used in the hbasemath
 */
public interface Constants {

  /**
   * An empty instance.
   */
  public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  
  /**
   * Hbase Structure for matrices 
   */
  
  /** Meta-columnFamily to store the matrix-info */
  public final static String METADATA = "metadata";

  /** Column index & attributes */
  public final static String CINDEX = "cIndex";

  /** The attribute column family */
  public static byte[] ATTRIBUTE = Bytes.toBytes("attribute");

  /** The type of the matrix */
  public final static String METADATA_TYPE = "type";
  
  /** The reference of the matrix */
  /** (1) when we create a Matrix object, we set up a connection to hbase table,
   *      the reference of the table will be incremented.
   *  (2) when we close a Matrix object, we disconnect the hbase table, 
   *      the reference of the table will be decremented.
   *      i)  if the reference of the table is not zero:
   *          we should not delete the table, because some other matrix object
   *          connect to the table.
   *      ii) if the reference of the table is zero:
   *          we need to know if the matrix table is aliased.
   *          1) if the matrix table is aliased, we should not delete the table.
   *          2) if the matrix table is not aliased, we need to delete the table.
   */
  public final static String METADATA_REFERENCE = "reference";
  
  /** The aliase names column family */
  public final static String ALIASEFAMILY = "aliase";
  
  /** Default columnFamily name */
  public static byte[] COLUMNFAMILY = Bytes.toBytes("column");
  
  /** Temporary random matrices name prefix */
  public final static String RANDOM = "rand";

  /** Admin table name */
  public final static String ADMINTABLE = "hbasemath.admin.table";

  /** Matrix path columnFamily */
  public static final String PATHCOLUMN = "path";

  /** Temporary Aliase name prefix in Hama Shell */
  public static final String RANDOMALIASE = "_";
  
  /** default matrix's path length (tablename length) */
  public static final int DEFAULT_PATH_LENGTH = 5;
  
  /** default matrix's max path length (tablename length) */
  public static final int DEFAULT_MAXPATHLEN = 10000;
  
  /** default try times to generate a suitable tablename */
  public static final int DEFAULT_TRY_TIMES = 10000000;
  
  /** block data column */
  public static final String BLOCK = "block";
  
  public static final Text ROWCOUNT= new Text("row");
}

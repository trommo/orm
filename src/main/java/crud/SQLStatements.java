package crud;

public class SQLStatements {
    public static final String DELETE_BY_ID = "DELETE FROM %s WHERE %s = %s";
    public static final String SELECT_BY_ID = "SELECT * FROM %s WHERE %s = %d";
    public static final String INSERT_NEW_OBJECT = "INSERT into %s (%s) VALUES (%s)";
}

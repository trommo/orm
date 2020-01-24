package mapping;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeConverter {

    public void convertDataSqlToJava(Object object, Field field, ResultSet resultSet, int colNumber) {
        Class<?>fieldType = field.getType();
        int columnTypeNumber = 0;
        try {
            columnTypeNumber = resultSet.getMetaData().getColumnType(colNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (columnTypeNumber == -7) {
            String colType;
            try {
                colType = resultSet.getMetaData().getColumnTypeName(colNumber);
                if (colType.equals("bool")) {
                    columnTypeNumber = 16;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (DataBaseType dataBaseType : DataBaseType.values()
        ) {
            if (isSameType(columnTypeNumber, dataBaseType, fieldType)) {
                try {
                    dataBaseType.convertData(object, field, resultSet, colNumber);
                } catch (SQLException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isSameType(int colNumber, DataBaseType dataBaseType, Class<?> fieldType) {
        return colNumber == dataBaseType.getTypeNumber() && (fieldType.isAssignableFrom(dataBaseType.getClassWrapper())) || fieldType.getName().equals(dataBaseType.getNameJavaTypePrimitive());
    }


}

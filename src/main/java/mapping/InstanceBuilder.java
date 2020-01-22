package mapping;

import exception.OrmSoftException;
import metadata.*;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class InstanceBuilder {
    IdData idData;
    List<FieldData> fieldDataList;
    List<JoinColumnData> joinColumnDataList;

    public void createInstance(Object objectNew, ResultSet resultSet, TableData tableData) throws SQLException {
        Class<?> classobj = objectNew.getClass();
        idData = tableData.getIdData();
        fieldDataList = tableData.getFieldDataList();
        joinColumnDataList = tableData.getJoinColumnDataList();
        ResultSetMetaData rsMeta = resultSet.getMetaData();
        int countCol = rsMeta.getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i <= countCol; i++) {
                String columnName = rsMeta.getColumnName(i);
                String fieldName = getFieldName(columnName);

                Field field;
                try {
                    field = objectNew.getClass().getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    throw new OrmSoftException("Class '" + classobj + "' does not have field" + fieldName + "'");
                }
                Class<?> fieldType = field.getType();
                field.setAccessible(true);
                setValue(objectNew, field, resultSet, fieldType, i);
            }
        }

    }

    private static void setValue(Object objectNew, Field field, ResultSet resultSet, Class<?> fieldType, int colNumber) {
        TypeConverter typeConverter = new TypeConverter();
        typeConverter.convertDataSqlToJava(objectNew, field, resultSet, fieldType, colNumber);
    }

    private String getFieldName(String nameColumn) {
        String nameField = null;
        if (checkFieldName(nameColumn, idData)) {
            nameField = idData.getNameField();
        } else if (fieldDataList != null) {
            for (FieldData fieldData : fieldDataList
            ) {
                if (checkFieldName(nameColumn, fieldData)) {
                    nameField = fieldData.getNameField();
                }
            }
        } if (joinColumnDataList != null) {
            for (JoinColumnData joinColumnData : joinColumnDataList
            ) {
                if (checkFieldName(nameColumn, joinColumnData)) {
                    nameField = joinColumnData.getNameField();
                }
            }
        }
        return nameField;
    }


    private boolean checkFieldName(String nameColumn, DataHolder dataHolder) {
        return dataHolder.getNameColumn().equals(nameColumn);

    }

}

package crud;

import connection.DbConnection;
import exception.AnnotationException;
import exception.OrmSoftException;
import metadata.FieldData;
import metadata.JoinColumnData;
import metadata.MetaDataSchema;
import metadata.TableData;
import model.User;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class UpdateData {
    public void updateRecord(Object object) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        List<String> columnName = new LinkedList<>();
        List<String> columnValue = new LinkedList<>();
        List<FieldData> fieldDataList;
        Class<?> classobj = object.getClass();
        TableData tableData = MetaDataSchema.getTableData(object);

        try {
            fieldDataList = MetaDataSchema.getClassMap().get(classobj).getFieldDataList();
        } catch (NullPointerException e) {
            throw new AnnotationException("Class '" + classobj.getSimpleName() + "' does not exist in MetaDataSchema");
        }

        String tableName = MetaDataSchema.getTableData(object).getTableName();

        for (FieldData fieldData : fieldDataList
        ) {
            Field field;
            try {
                field = classobj.getDeclaredField(fieldData.getNameField());
            } catch (NoSuchFieldException e) {
                throw new OrmSoftException("Instanse of Class '" + classobj + "' does not have field '" + fieldData.getNameField() + "'");
            }
            field.setAccessible(true);
            columnName.add(fieldData.getNameColumn());
            columnValue.add(field.get(object).toString());
        }

        if (tableData.getJoinColumnDataList() != null) {
            for (JoinColumnData joinVolumnData : tableData.getJoinColumnDataList()
            ) {
                String fieldName = joinVolumnData.getNameField();
                String nameColumn = joinVolumnData.getNameColumn();
                Class<?> classObj2 = joinVolumnData.getTypeClass();
                String nameIdField = MetaDataSchema.getIdName(classObj2.newInstance());
                Field fieldIdFK = classObj2.getDeclaredField(nameIdField);
                fieldIdFK.setAccessible(true);
                Field fieldRel = object.getClass().getDeclaredField(fieldName);
                fieldRel.setAccessible(true);
                Object objectRel = fieldRel.get(object);
                fieldIdFK.getInt(objectRel);
                int id = fieldIdFK.getInt(objectRel);
                columnName.add(nameColumn);
                columnValue.add(Integer.toString(id));
            }
        }


        String query = String.format("INSERT into %s (%s) VALUES (%s)", tableName, listColumnsToString(columnName), listValuesToString(columnValue));

        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        Field fieldId;
        try {
            fieldId = classobj.getDeclaredField(MetaDataSchema.getIdName(object));
        } catch (NoSuchFieldException e) {
            throw new OrmSoftException("Instanse of Class '" + classobj + "' does not have ID field");
        }
        fieldId.setAccessible(true);
        int result = preparedStatement.executeUpdate();

        if (result == 0) {
            throw new OrmSoftException("Creating record of Class '" + object.getClass() +  "' failed, no rows affected.");
        }

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                fieldId.setInt(object, (int) generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Creating ID record of Class '" + classobj +  "'failed, no ID obtained.");
            }
        }

        try {
            preparedStatement.close();
            connection.close();
        } finally {
            if(preparedStatement !=null){
                preparedStatement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }


    }

    public static String listColumnsToString(List<String> linkedList){
        Iterator<String> listIter = linkedList.iterator();
        StringBuilder sb = new StringBuilder();
        for (;;) {
            String st = listIter.next();
            sb.append(st);
            if (!listIter.hasNext())
                return sb.toString();
            sb.append(", ");
        }
    }

    public static String listValuesToString(List <String> linkedList){
        Iterator<String> listIter = linkedList.iterator();
        StringBuilder sb = new StringBuilder();
        for (;;) {
            String st = listIter.next();
            sb.append("'").append(st).append("'");
            if (!listIter.hasNext())
                return sb.toString();
            sb.append(", ");
        }
    }

}

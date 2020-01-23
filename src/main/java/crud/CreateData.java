package crud;

import connection.DbConnection;
import exception.AnnotationException;
import exception.OrmSoftException;
import mapping.InstanceBuilder;
import metadata.MetaDataSchema;
import metadata.TableData;

import java.sql.*;

public class CreateData {

    public Object createInstanceFromRecordById(Class<?> classobj, int id) throws SQLException, IllegalAccessException, InstantiationException {
        Object object = classobj.newInstance();
        TableData tableData;
        String tableName;
        try {
            tableData = MetaDataSchema.getTableData(classobj.newInstance());
            tableName = tableData.getTableName();
        } catch (Exception e) {
            throw new AnnotationException("Class '" + classobj.getName() + "' does not exist in MetaDataSchema");
        }

        String nameId;
        try {
            nameId = tableData.getIdData().getNameColumn();
        } catch (Exception e) {
            throw new AnnotationException("Class '" + classobj.getName() + "' does not have annotation for id field");
        }

        String query = String.format("SELECT * from %s WHERE %s = %d", tableName, nameId, id);

        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()) {
            throw new OrmSoftException("The record with ID '" + id + "' does not exist in table '" + tableName + "'");
        }
        InstanceBuilder instanceBuilder = new InstanceBuilder();
        instanceBuilder.createInstance(object, resultSet, tableData);


        try {
            preparedStatement.close();
            connection.close();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return object;
    }
}

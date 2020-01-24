package crud;

import exception.AnnotationException;
import mapping.InstanceBuilder;
import model.Auto;
import model.User;
import reflection.DataClassBuilder;

import java.sql.SQLException;

public class EntityManager {

    public EntityManager() throws AnnotationException {
        DataClassBuilder dataClassBuilder = new DataClassBuilder();
        dataClassBuilder.addAnnotationClass(User.class);
        dataClassBuilder.addAnnotationClass(Auto.class);
        dataClassBuilder.bulidConfig();
    }

    public void deleteById (Class<?> classobj, Integer id) throws IllegalAccessException, SQLException, InstantiationException {
        CRUDOperations crudOperations = new CRUDOperations();
        crudOperations.deleteById(classobj, id);
    }

    public void save(Object object) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        UpdateData updateData = new UpdateData();
        updateData.updateRecord(object);
    }

    public Object loadByValue (Class<?> classobj, String columnName, String value) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        CreateData createData = new CreateData();
        Object object = createData.createInstanceByValue(classobj, columnName, value);;
        InstanceBuilder.joinGet = null;
        return object;
    }

    public Object loadById (Class<?> classobj, int value) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        CreateData createData = new CreateData();
        Object object = createData.createInstanceFromRecordById(classobj, value);
        InstanceBuilder.joinGet = null;
        return object;
    }
}

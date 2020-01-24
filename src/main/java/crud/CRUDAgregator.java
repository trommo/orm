package crud;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CRUDAgregator {
    public CRUDAgregator() {

    }

    public void create(Object object) throws SQLException, IllegalAccessException {
        Creator creator = new Creator();
        creator.createRecord(object);
    }

    public Object read(Class <?> classobj, Integer id) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        CreateData createData = new CreateData();
        return createData.createInstanceFromRecordById(classobj, id);
/*        Reader reader = new Reader();
        return reader.createInstanceFromRecordById(object, id);*/
    }

    public void update(Object object) throws SQLException, IllegalAccessException {
        Updater updater = new Updater();
        updater.updateRecord(object);
    }

    public void delete(Object object, Integer id) throws SQLException {
        Remover remover = new Remover();
        remover.deleteById(object, id);
    }
}

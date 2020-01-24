package crud;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class aggregates the basic CRUD-methods
 *
 * @author Anna Severyna
 */
public class CRUDAggregator {
    public CRUDAggregator() {

    }

    /**
     * Method writes objects fields data to database
     *
     * @param   object    object whose fields values need to be written to the database
     * @throws  SQLException
     * @throws  IllegalAccessException
     */
    public void create(Object object) throws SQLException, IllegalAccessException {
        Creator creator = new Creator();
        creator.createRecord(object);
    }

    /**
     *  Method reads the object data fields values in database table
     *
     * @param   object      object as indicator of database table
     * @param   id          object id in database table
     * @return              object with fields values from database table
     * @throws  SQLException
     * @throws  IllegalAccessException
     * @throws  InstantiationException
     */
    public Object read(Object object, Integer id) throws SQLException, IllegalAccessException, InstantiationException {
        Reader reader = new Reader();
        return reader.createInstanceFromRecordById(object, id);
    }

    /**
     *  Method updates objects fields data in database table
     *
     * @param   object      object
     * @throws  SQLException
     * @throws  IllegalAccessException
     */
    public void update(Object object) throws SQLException, IllegalAccessException {
        Updater updater = new Updater();
        updater.updateRecord(object);
    }

    /**
     *  Method removes object data from database table
     *
     * @param   object      object as indicator of database table
     * @param   id          object id in database table
     * @throws  SQLException
     */
    public void delete(Object object, Integer id) throws SQLException {
        Remover remover = new Remover();
        remover.deleteById(object, id);
    }
}

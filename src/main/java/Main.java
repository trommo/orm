import crud.CreateData;
import crud.UpdateData;
import model.Auto;
import model.User;
import reflection.DataClassBuilder;

import java.sql.*;


public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException, InstantiationException {


        // test insert for method
        DataClassBuilder dataClassBuilder = new DataClassBuilder();
        dataClassBuilder.addAnnotationClass(User.class);
        dataClassBuilder.addAnnotationClass(Auto.class);
        dataClassBuilder.bulidConfig();
        int number = Types.ARRAY;



        User user2 = new User("Mary", 20, "Casablanca");
        user2.setId(2);
        CreateData createData = new CreateData();
        User user3 = (User) createData.createInstanceFromRecordById(User.class, 3);
        //System.out.println("Print user3 from main: " + user3);
        Auto auto3 = (Auto) createData.createInstanceFromRecordById(Auto.class, 2);
       // Auto auto2 = new Auto("Opel", "white", false, user2);
/*        UpdateData updateData = new UpdateData();
        updateData.updateRecord(auto2);*/
        System.out.println(auto3);
        System.out.println(user3);
    }
}

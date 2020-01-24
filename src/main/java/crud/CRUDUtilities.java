package crud;

import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

public class CRUDUtilities {

    public static String listColumnsToString(List<String> linkedList){
        Iterator<String> listIter = linkedList.iterator();
        StringJoiner sj = new StringJoiner(", ");
        while (listIter.hasNext()) {
            String st = listIter.next();
            sj.add(st);
        }
        return sj.toString().trim();
    }


    public static String listValuesToString(List <String> linkedList){
        Iterator<String> listIter = linkedList.iterator();
        StringJoiner sj = new StringJoiner(", ");
        while (listIter.hasNext()) {
            String st = listIter.next();
            sj.add("'" +  st + "'");
        }
        return sj.toString().trim();
    }
}

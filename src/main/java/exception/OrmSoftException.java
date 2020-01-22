package exception;

import java.sql.SQLException;

/**
 * Ошибка по работе ORM со стороны клиента.
 * */

public class OrmSoftException extends SQLException{
    public OrmSoftException(String message) {
        super(message);
    }
}

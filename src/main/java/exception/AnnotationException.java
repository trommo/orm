package exception;

import java.sql.SQLException;

/**
 * Ошибка по аннотациям. Пока не уверена, что должно быть несколько эксепшенов для разных направлений,
 * может потом это будет просто один класс для всех, что-то типа ORMSoftException
 * */

public class AnnotationException extends SQLException{
    public AnnotationException(String message) {
        super(message);
    }
}

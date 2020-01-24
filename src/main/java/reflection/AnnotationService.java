package reflection;

import annotation.*;
import exception.AnnotationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * Проверка класса на наличие аннотаций
 */

public class AnnotationService {


    /**
     * Общая проверка на все аннотации, чтобы передать все классы на конфигурацию
     */

    public void checkAllAnnotation(Set<Class<?>> listClassAnnotaition) throws AnnotationException {
        for (Class<?> classAnnotation : listClassAnnotaition
        ) {
            checkTableAnnotation(classAnnotation);
            checkField(classAnnotation);
        }
    }

    /**
     * Проверка на наличие анностации EntitySoft
     *
     * @see TableSoft
     */
    private void checkTableAnnotation(Class<?> classTable) throws AnnotationException {
        if (classTable.getAnnotation(TableSoft.class) == null) {
            throw new AnnotationException("Class '" + classTable.getSimpleName() + "' does not have annotation @TableSoft");
        }
    }

    /**
     * Проверка на наличие полей в классе
     * *
     */

    private void checkField(Class<?> classField) throws AnnotationException {
        Field[] fields = classField.getDeclaredFields();
        if (fields.length == 0) {
            throw new AnnotationException("Class '" + classField.getSimpleName() + "' has no fields");
        }
    }
}

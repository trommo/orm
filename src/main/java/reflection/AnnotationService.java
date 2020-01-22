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
            checkEntityAnnotation(classAnnotation);
            checkTableAnnotation(classAnnotation);
            checkField(classAnnotation);
        }
    }

    /**
     * Проверка на наличие анностации EntitySoft
     *
     * @see EntitySoft
     */
    private void checkEntityAnnotation(Class<?> classEntity) throws AnnotationException {
        if (classEntity.getAnnotation(EntitySoft.class) == null) {
            throw new AnnotationException("Class '" + classEntity.getSimpleName() + "' does not have annotation @EntitySoft");
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
     * Проверка на наличие аннотаций и Field
     * Пока только проверка на наличие полей
     * По идее поле может быть без аннотации, если оно конвертируемого типа
     * и его имя совпадает с названием колонки.
     * *
     *
     * @see annotation.ColumnSoft
     * @see annotation.ExcludeSoft
     * @see annotation.IdSoft
     * @see annotation.ManyToOneSoft
     * @see annotation.OneToManySoft
     * @see annotation.OneToOneSoft
     */

    private void checkField(Class<?> classField) throws AnnotationException {
        Field[] fields = classField.getDeclaredFields();
        if (fields.length == 0) {
            throw new AnnotationException("Class '" + classField.getSimpleName() + "' has no fields");
        }
    }
}

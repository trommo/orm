package reflection;

import annotation.*;
import exception.AnnotationException;
import metadata.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Класс для создания метаданных по аннотированным классам
 */

public class MetaDataService {
    private String tableName;
    private IdData idData;
    private List<FieldData> fieldsDataList;
    private List<JoinColumnData> joinColumnDataList;
    private List<ForeignKeyData> foreignKeyDataList;
    private TableData tableData;

    public void createMetaDataSchema(Set<Class<?>> listClassAnnotation) throws AnnotationException {
        for (Class<?> annotatedClass : listClassAnnotation
        ) {
            createTableData(annotatedClass);
            MetaDataSchema.addToClassMap(annotatedClass, tableData);
        }
    }

    /**
     * Метод для создания данных таблицы
     */
    private void createTableData(Class<?> annotatedClass) throws AnnotationException {
        setTableName(annotatedClass);
        fieldsDataList = new ArrayList<>();
        joinColumnDataList = new ArrayList<>();
        foreignKeyDataList = new ArrayList<>();
        addFields(annotatedClass);
        tableData = new TableData(tableName, idData, fieldsDataList, joinColumnDataList, foreignKeyDataList);
    }

    /**
     * Метод для добавления имени таблицы
     */
    private void setTableName(Class<?> classAnnotation) {
        TableSoft tableSoft = classAnnotation.getAnnotation(TableSoft.class);
        if (tableSoft.name().equals("")) {
            tableName = classAnnotation.getSimpleName().toLowerCase();
        } else {
            tableName = tableSoft.name();
        }
    }

    /**
     * Добавляем в таблицу данных поля
     */
    private void addFields(Class<?> annotatedClass) throws AnnotationException {
        Field[] fields = annotatedClass.getDeclaredFields();
        for (Field field : fields
        ) {
            if (isIdInBase(field)) {
                addIdField(field);
            } else if (isColumnInBase(field)) {
                addColumnFields(field);
            } else if (isJoinColumn(field)) {
                addJoinColumnFields(field);
            } else if (isForeignKey(field)) {
                addForeignKeyFields(field);
            }
        }
    }


    /**
     * Добавление поля для первичного ключа
     */
    private void addIdField(Field field) {
        this.idData = new IdData(field.getName(), field.getType().getSimpleName());
        idData.setNameColumn(setColumnName(field, idData));
        idData.setTypeClass(field.getType());
    }


    /**
     * Добавляем в таблицу поля, которые соответствуют колонкам
     */
    private void addColumnFields(Field field) {
        FieldData fieldData = new FieldData(field.getName(), field.getType().getSimpleName());
        fieldData.setNameColumn(setColumnName(field, fieldData));
        fieldData.setTypeClass(field.getType());
        fieldsDataList.add(fieldData);
    }

    /**
     * Добавляем в таблицу связанные поля по внешнему ключу
     */
    private void addJoinColumnFields(Field field) throws AnnotationException {
        Annotation[] annotations = field.getDeclaredAnnotations();
        if (annotations.length <= 1) {
            throw new AnnotationException("Field '" + field.getName() + "' from Class '" + field.getDeclaringClass() + "' does not have relation annotation for JoinColumn");
        }
        String relation = getRelation(field);
        JoinColumnData joinFieldData = new JoinColumnData(field.getName(), field.getType().getSimpleName(), relation);
        String columnName = field.getDeclaredAnnotation(JoinColumnSoft.class).name();
        if (columnName.equals("")) {
            columnName = joinFieldData.getNameField().toLowerCase();
        }
        joinFieldData.setNameColumn(columnName);
        joinFieldData.setTypeClass(field.getType());
        joinColumnDataList.add(joinFieldData);
    }

    /**
     * Добавляем в таблицу внешнего ключа
     */
    private void addForeignKeyFields(Field field) throws AnnotationException {
        String relation = getRelation(field);
        ForeignKeyData foreignKeyData = new ForeignKeyData(field.getName(), field.getType().getSimpleName(), relation);
        String columnName = field.getDeclaredAnnotation(OneToManySoft.class).mappedBy();
        if (columnName.equals("")) {
            columnName = foreignKeyData.getNameField().toLowerCase();
        }
        foreignKeyData.setNameColumn(columnName);
        ParameterizedType genericFieldType = (ParameterizedType) field.getGenericType();
        Type[] fieldArgTypes = genericFieldType.getActualTypeArguments();
        for (Type fieldArgType : fieldArgTypes) {
            Class<?> fieldArgClass = (Class<?>) fieldArgType;
            foreignKeyData.setTypeClass(fieldArgClass);
        }
        foreignKeyDataList.add(foreignKeyData);
    }

    /**
     * Метод для получения типа связи
     */

    private String getRelation(Field field) throws AnnotationException {

        String relation = null;
        if (isOneToOneRel(field)) {
            relation = "OneToOne";
        } else if (isOneToManyRel(field)) {
            relation = "OneToMany";
        } else if (isManyToOneRel(field)) {
            relation = "ManyToOne";
        } else if (isManyToManyRel(field)) {
            relation = "ManyToMany";
        }
        if (relation == null) {
            throw new AnnotationException("Field '" + field.getName() + "' from Class '" + field.getDeclaringClass() + "' does not have relation annotation");
        }
        return relation;
    }

    /**
     * Метод для добавления имени колонки для поля
     */
    private String setColumnName(Field field, DataHolder dataHolder) {
        String columnName;
        try {
            columnName = field.getDeclaredAnnotation(ColumnSoft.class).name();
        } catch (NullPointerException e) {
            columnName = field.getName().toLowerCase();
        }
        if (columnName.equals("")) {
            columnName = dataHolder.getNameField().toLowerCase();
        }
        return columnName;
    }


    /**
     * Проверка на аннотацию @IdSoft
     * Эта аннотация для полей, которые являются первичным ключом
     *
     * @see annotation.IdSoft
     */
    private boolean isIdInBase(Field field) {
        return field.getDeclaredAnnotation(IdSoft.class) != null;
    }


    /**
     * Проверка на аннотацию @ColumnSoft
     * Эта аннотация для полей, которые соответствуют колонкам в БД
     *
     * @see annotation.ColumnSoft
     */
    private boolean isColumnInBase(Field field) {
        return field.getDeclaredAnnotation(ColumnSoft.class) != null;
    }


    /**
     * Проверка на аннотацию @ForeignKeySoft
     * Эта аннотация для внешних ключей
     *
     * @see annotation.ForeignKeySoft
     */
    private boolean isForeignKey(Field field) {
        return field.getDeclaredAnnotation(ForeignKeySoft.class) != null;
    }

    /**
     * Проверка на аннотацию @JoinColumnSoft
     * Эта аннотация для связанной таблицы по внешнему ключу
     *
     * @see annotation.JoinColumnSoft
     */
    private boolean isJoinColumn(Field field) {
        return field.getDeclaredAnnotation(JoinColumnSoft.class) != null;
    }

    /**
     * Проверка на аннотацию @OneToOneSoft
     *
     * @see annotation.OneToOneSoft
     */
    private boolean isOneToOneRel(Field field) {
        return field.getDeclaredAnnotation(OneToOneSoft.class) != null;
    }

    /**
     * Проверка на аннотацию @OneToManySoft
     *
     * @see annotation.OneToManySoft
     */
    private boolean isOneToManyRel(Field field) {
        return field.getDeclaredAnnotation(OneToManySoft.class) != null;
    }

    /**
     * Проверка на аннотацию @ManyToOneSoft
     *
     * @see annotation.ManyToOneSoft
     */
    private boolean isManyToOneRel(Field field) {
        return field.getDeclaredAnnotation(ManyToOneSoft.class) != null;
    }

    /**
     * Проверка на аннотацию @ManyToManySoft
     *
     * @see annotation.ManyToManySoft
     */
    private boolean isManyToManyRel(Field field) {
        return field.getDeclaredAnnotation(ManyToManySoft.class) != null;
    }


}





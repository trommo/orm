package metadata;

/**
 * Здесь хранятся данные о полях, полученные с помощью рефлексии. Пока тут только пара имя поля и тип. Но со временем
 * вижу, что будет расширение
 * */

public class FieldData implements DataHolder{
    private String nameField;
    private String type;
    private String annotation;
    private String nameColumn;


    public FieldData(String nameField, String type) {
        this.nameField = nameField;
        this.type = type;
    }

    public FieldData(String nameField, String type, String annotation) {
        this.nameField = nameField;
        this.type = type;
        this.annotation = annotation;
    }


    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "FieldData {" +
                "nameField='" + nameField + '\'' +
                ", nameColumn='" + nameColumn + '\'' +
                ", type='" + type + '\'' +
                //", annotation='" + annotation + '\'' +
                '}';
    }


}

package metadata;

public interface DataHolder {
    String getNameField();
    void setNameField(String nameField);
    String getNameColumn();
    void setNameColumn(String nameColumn);
    String getType();
    void setType(String type);
    Class<?> getTypeClass();
    void setTypeClass(Class<?> typeClass);
}

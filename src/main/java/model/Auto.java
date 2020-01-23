package model;

import annotation.*;

@TableSoft (name = "autos")
public class Auto {

    @IdSoft()
    private int id;

    @ColumnSoft()
    private String model;

    @ColumnSoft()
    private String color;

    @ColumnSoft()
    private boolean status;

    @JoinColumnSoft(name = "user_id")
    @ManyToOneSoft()
    private User user;

    public Auto() {
    }

    public Auto(String model, String color, boolean status, User user) {
        this.model = model;
        this.color = color;
        this.status = status;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}

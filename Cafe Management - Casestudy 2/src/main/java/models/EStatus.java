package models;

public enum EStatus {
    DELIVERING(1,"Đang giao"),DONE(2,"hoàn thành");
    private int id;
    private String name;

    EStatus(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

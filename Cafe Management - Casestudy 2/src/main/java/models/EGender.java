package models;

public enum EGender {
    MALE(1, "Nam"), FEMALE (2, "Nữ"), OTHER(3, "Khác");
    private final int id;
    private final String name;

    private EGender(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static EGender findById(int id){
        for(EGender e : values()){
            if(e.getId() == id){
                return e;
            }
        }
        return null;
    }
}

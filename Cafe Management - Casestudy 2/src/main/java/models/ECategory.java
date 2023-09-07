package models;

public enum ECategory {
    CAFE(1, "CAFE"),TEA(2, "TEA"),FREEZE(3, "FREEZE");
    private final int id;
    private String name;
    private ECategory(int id, String name){
        this.id = id;
        this.name = name;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public static ECategory findById(int id){
        for (ECategory e : values())
            if(e.getId() == id){
                return e;
            }
        return null;
    }
}

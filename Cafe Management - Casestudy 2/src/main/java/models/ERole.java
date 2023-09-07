package models;



public enum ERole {
    ADMIN(1,"ADMIN"), USER(2,"USER");

    private int id;
    private String name;
    private ERole(int id, String name){
        this.id = id;
        this.name = name;
    }
    public int getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public static ERole findById(int id){
        for(ERole r : values()){
            if(r.getId() == id){
                return r;
            }
        }
        return null;
    }
}

package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.DateUtils;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Products implements IParseModel<Products>{
    private int id;
    private String name;
    private int price;
    private ECategory category;

    @Override
    public Products parse(String line) {
        String[] items = line.split(",");
        Products p = null;
        try{
            p = new Products(Integer.parseInt(items[0]),items[1],
                    Integer.parseInt(items[2]),ECategory.valueOf(items[3]));
        }catch (Exception e){
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public String toString() {
//        7,Trà thạch đào,55000,TEA
        return String.format("%s,%s,%s,%s", this.id, this.name, this.price,this.category);
    }
}

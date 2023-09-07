package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RateProduct implements IParseModel{
    private long id;
    private int idProduct;
    private String username;
    private int rate;


    @Override
    public Object parse(String line) {
        //1,1,4
        String[] item = line.split(",");
        RateProduct rp = null;
        try{
            rp = new RateProduct(Long.parseLong(item[0]), Integer.parseInt(item[1]),
                    item[2],Integer.parseInt(item[3]));
        }catch (Exception e){
            e.printStackTrace();
        }
        return rp;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s",this.id,this.idProduct,this.username,this.rate);
    }
}

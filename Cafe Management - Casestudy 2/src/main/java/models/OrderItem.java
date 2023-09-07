package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem implements IParseModel{
    private long id;
    private long idOrder;
    private int idProduct;
    private int quantityItem;
    private long price;

//    @Override
    public OrderItem parse(String line) {
        //2,2,3,5,100000
        String[] items = line.split(",");
        OrderItem orderItem = null;
        try {
            orderItem = new OrderItem(Long.parseLong(items[0]),Long.parseLong(items[1]),
                    Integer.parseInt(items[2]),Integer.parseInt(items[3]),Long.parseLong(items[4]));
        }catch (Exception e){
            e.printStackTrace();;
        }
        return orderItem;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s",
                this.id,this.idOrder,this.idProduct,this.quantityItem,this.price);
    }
}

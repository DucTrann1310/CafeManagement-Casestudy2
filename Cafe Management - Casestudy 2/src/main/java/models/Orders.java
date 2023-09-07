package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
    @NoArgsConstructor
@Setter
@Getter
public class Orders implements IParseModel<Orders>{
    private long id;
    private LocalDate createAt;
    private long total;
    private String name;
    private String phone;
    private String username;
    private EStatus eStatus;
    List<OrderItem> orderItems;

    @Override
    public Orders parse(String line) {
        //1,23-08-2023,144000,Lê Công Vinh,01242257854,username1,Hoàn thành,<List>
        String[] items = line.split(",");
        Orders order = null;
        try{
            order = new Orders(Long.parseLong(items[0]), DateUtils.parseDate(items[1]),Long.parseLong(items[2])
                    ,items[3],items[4],items[5],EStatus.valueOf(items[6]),null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return order;
    }
    public void setTotalPrice(){
        long total = 0;
        if(this.getOrderItems() != null){
            for(OrderItem ot : orderItems){
                total += ot.getQuantityItem()*ot.getPrice();
            }
        }
        this.total = total;
    }

    @Override
    public String toString() {
        //1,23-08-2023,144000,Lê Công Vinh,01242257854,username1,hoàn thành,<List>
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                this.id,DateUtils.formatDate(this.createAt),this.total,this.name,this.phone,this.username,this.eStatus);
    }
}

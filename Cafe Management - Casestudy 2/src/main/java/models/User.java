package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.DateUtils;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User implements IParseModel<User> {
    private int id;
    private String name;
    private String phone;
    private String username;
    private String password;
    private LocalDate dob;
    private EGender gender;
    private ERole role;

    @Override
    public User parse(String line) {
        String[] items = line.split(",");
        User u = null;
        try {
            u = new User(Integer.parseInt(items[0]),items[1],items[2],items[3],items[4],
                DateUtils.parseDate(items[5]), EGender.valueOf(items[6]),ERole.valueOf(items[7]));
        }catch (Exception e){
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public String toString() {
        //2,Phan Văn Tài,0123456789,vantai123,vantai123,25-01-1990,MALE,CASHIER
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s", this.id, this.name, this.phone, this.username,
                this.password, DateUtils.formatDate(this.dob),this.gender,this.role);
    }
}

package views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import models.*;
import service.*;
import utils.*;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class UserView {
    private Scanner scanner = new Scanner(System.in);
    private IUserService iUserService;
    private IOrderService iOrderService;
    private IRateProductService iRateProductService;

    public UserView() {
        iUserService = new UserService();
        iOrderService = new OrderService();
        iRateProductService = new RateProductService();
    }
    LoginView loginView = new LoginView();
    public void launcher() {
        if(AuthUtils.hasRole(ERole.ADMIN)){
            displayAdminView();
        }
        if(AuthUtils.hasRole(ERole.USER)){
            displayUserView();
        }
    }
    private void displayAdminView(){
        System.out.println("---------------------------------------------");
        System.out.println("-          Menu quản lý user:               -");
        System.out.println("-          Nhập 1. Xem danh sách user       -");
        System.out.println("-          Nhập 2. Thêm user                -");
        System.out.println("-          Nhập 3. Sửa user                 -");
        System.out.println("-          Nhập 4. Xóa user                 -");
        System.out.println("-          Nhập 5: Sắp xếp user             -");
        System.out.println("-          Nhập 6: Tìm kiếm user            -");
        System.out.println("-          Nhập 7. Quay lại                 -");
        System.out.println("-          Chọn:                            -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,7);
        switch (actionMenu) {
            case 1:
                showUser();
                launcher();
                break;
            case 2:
                addUser();
                break;
            case 3:
                updateUser();
                break;
            case 4:
                deleteUser();
                launcher();
                break;
            case 5:
                sortUser();
                launcher();
                break;
            case 6:
                findUser();
                break;
            case 7:
                loginView.adminView();
                break;
        }
    }
    private void displayUserView(){
        System.out.println("---------------------------------------------");
        System.out.println("-        Tùy chọn:                          -");
        System.out.println("-        Nhập 1. Xem thông tin cá nhân      -");
        System.out.println("-        Nhập 2. Sửa thông tin              -");
        System.out.println("-        Nhập 3. Quay lại                   -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,3);
        switch (actionMenu){
            case 1:
                showPersonalInfo();
                launcher();
                break;
            case 2:
                updateUser();
                break;
            case 3:
                loginView.userView();
                break;
        }
    }

    private void showPersonalInfo() {
        displayPersonalInfo(AuthUtils.getUserAuthenticator());

    }

    private void findUser() {
        System.out.println("---------------------------------------------");
        System.out.println("-          Menu tìm kếm user:               -");
        System.out.println("-          Nhập 1. Theo id                  -");
        System.out.println("-          Nhập 2. Theo tên                 -");
        System.out.println("-          Nhập 3. Theo số điện thoại       -");
        System.out.println("-          Nhập 4. Theo ngày sinh           -");
        System.out.println("-          Nhập 5. Theo giới tính           -");
        System.out.println("-          Nhập 6. Quay lại                 -");
        System.out.println("-          Chọn:                            -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,6);
        switch (actionMenu){
            case 1:
                findUserById();
                launcher();
                break;
            case 2:
                findUserByName();
                launcher();
                break;
            case 3:
                findUserByPhone();
                launcher();
                break;
            case 4:
                findUserByDOB();
                launcher();
                break;
            case 5:
                findUserByGender();
                launcher();
                break;
            case 6:
                launcher();
                break;
        }
    }

    private void findUserByPhone() {
        String phone = checkInputValid(ValidateUtils.FIELD_PHONE,ValidateUtils.FIELD_PHONE_MASSAGE,ValidateUtils.REGEX_PHONE);

        List<User> foundUser = iUserService.findUserByPhone(phone);

        if(foundUser.isEmpty()){
            System.out.printf("Không tìm thấy danh sách người dùng với có số điện thoại %s\n",phone);
        }
        else{
            System.out.printf("Danh sách người dùng có số điện thoại %s là: \n",phone);
            displayUsers(foundUser);
        }
    }

    private void findUserByGender() {
        System.out.println("Nhập giới tính: ");
        for (EGender eGender : EGender.values()) {
            System.out.println(eGender.getName() + " " + eGender.getId());
        }
        int idGender = GetValue.getIntOfWithBounds(1,3);
        EGender gender = EGender.findById(idGender);

        List<User> foundUser = iUserService.findUserByGender(gender);

        if(foundUser.isEmpty()){
            System.out.printf("Không tìm thấy danh sách người dùng với có giới tính %s\n",gender);
        }
        else{
            System.out.printf("Danh sách người dùng có giới tính %s là: \n",gender);
            displayUsers(foundUser);
        }

    }

    private void findUserByDOB() {
        LocalDate dob = DateUtils.returnValidDOBUser();

        List<User> foundUser = iUserService.findUserByDOB(dob);

        if(foundUser.isEmpty()){
            System.out.printf("Không tìm thấy người dùng với có ngày sinh %s\n",dob);
        }
        else{
            System.out.printf("Danh sách người dùng có ngày sinh %s là: \n",dob);
            displayUsers(foundUser);
        }
    }


    private void findUserByName() {
//        List<User> users = iUserService.getAll();
        String strName = checkInputValid(ValidateUtils.FIELD_NAME,ValidateUtils.FIELD_NAME_MASSAGE,ValidateUtils.REGEX_NAME);

        List<User> foundUser = iUserService.findUserByName(strName);

        if(foundUser.isEmpty()){
            System.out.printf("Không tìm thấy người dùng với tên '%s'\n",strName);
        }
        else{
            System.out.printf("Danh sách người dùng có tên %s là: \n",strName);
            displayUsers(foundUser);
        }
    }

    private void findUserById() {
        List<User> users = iUserService.getAll();
        int idFind = GetValue.getIntOfWithBounds("Nhập id user muốn tìm kiếm: ",1,users.size());

        User foundUser = iUserService.findUserById(idFind);

        System.out.printf("Thông tin user có id %s là: \n", idFind);
        displayPersonalInfo(foundUser);
    }
    private void sortUser() {
        System.out.println("---------------------------------------------");
        System.out.println("-         Chọn kiểu muốn sắp xếp:           -");
        System.out.println("-         Nhập 1. Tăng dần                  -");
        System.out.println("-         Nhập 2. Giảm dần                  -");
        System.out.println("-         Nhập 3. Quay lại                  -");
        System.out.println("---------------------------------------------");
        List<User> users = iUserService.getAll();
        Comparator<User> comparator = null;
            int actionMenu = GetValue.getIntOfWithBounds(1,3);
            switch (actionMenu){
                case 1:
                    users.sort(sortUserAscending(comparator));
                    break;
                case 2:
                    users.sort(sortUserDescending(comparator));
                    break;
                case 3:
                    launcher();
                    break;
            }
            displayUsers(users);
    }
    private Comparator<User> sortUserDescending(Comparator<User> comparator) {
        System.out.println("---------------------------------------------");
        System.out.println("-          Bạn muốn sắp xếp theo:           -");
        System.out.println("-          Nhập 1. Tên                      -");
        System.out.println("-          Nhập 2. Ngày sinh                -");
        System.out.println("-          Nhập 3. Giới tính                -");
        System.out.println("-          Nhập 4. Quay lại                 -");
        System.out.println("-          Chọn:                            -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,5);

        switch (actionMenu){
            case 1:
                comparator = Comparator.comparing(User::getName).reversed();
                break;
            case 2:
                comparator = Comparator.comparing(User::getDob).reversed();
                break;
            case 3:
                comparator = Comparator.comparing(User::getGender).reversed();
                break;
            case 4:
                sortUser();
                break;
        }
        return comparator;
    }
    private Comparator<User> sortUserAscending(Comparator<User> comparator) {
        System.out.println("---------------------------------------------");
        System.out.println("-          Bạn muốn sắp xếp theo:           -");
        System.out.println("-          Nhập 1. Tên                      -");
        System.out.println("-          Nhập 2. Ngày sinh                -");
        System.out.println("-          Nhập 3. Giới tính                -");
        System.out.println("-          Nhập 4. Quay lại                 -");
        System.out.println("-          Chọn:                            -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,5);

        switch (actionMenu){
            case 1:
                comparator = Comparator.comparing(User::getName);
                break;
            case 2:
                comparator = Comparator.comparing(User::getDob);
                break;
            case 3:
                comparator = Comparator.comparing(User::getGender);
                break;
            case 4:
                sortUser();
                break;
        }
        return comparator;
    }
    private void displayPersonalInfo(User user){
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
        System.out.printf("- %5s | %25s | %15s | %15s | %15s | %10s | %10s -\n",
                "ID", "Name", "Phone", "Username", "DOB", "Gender","Role");
        System.out.printf("- %5s | %25s | %15s | %15s | %15s | %10s | %10s -\n",
                user.getId(), user.getName(),user.getPhone(), user.getUsername(),
                user.getDob(), user.getGender(),user.getRole());
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
    }
    private void displayUsers(List<User> users) {
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
        System.out.printf("- %5s | %25s | %15s | %15s | %15s | %10s | %10s -\n",
                "ID", "Name", "Phone", "Username", "DOB", "Gender","Role");
        for (User user : users) {
            System.out.printf("- %5s | %25s | %15s | %15s | %15s | %10s | %10s -\n",
                    user.getId(), user.getName(),user.getPhone(), user.getUsername(),
                    user.getDob(), user.getGender(),user.getRole());
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
    }
    private void deleteUser() {
        List<User> users = iUserService.getAll();
        showUser();
        int idDelete = GetValue.getIntOfWithBounds("Nhập id user muốn xóa: ",0,users.size());

        //Tìm kiếm tới user có id muốn xóa
        User foundUser = iUserService.findUserById(idDelete);
        //Lấy ra username của user muốn xóa
        String strUsername = foundUser.getUsername();
        //Xóa username ở file order và file rateproduct
        iOrderService.upadteUsername(strUsername);
        iRateProductService.updateRateProductUsername(strUsername);

        iUserService.deleteUser(idDelete);
        System.out.printf("Xóa thành công user có id %s\n", idDelete);
        showUser();
        }

    private void updateUser() {
        if(AuthUtils.hasRole(ERole.ADMIN)){
            List<User> users = iUserService.getAll();
//            List<Orders> orders = iOrderService.getAll();
            showUser();

            int idUpdate = GetValue.getIntOfWithBounds("Nhập id muốn sửa",1, users.size());

            User user1 = getUserBasicUpdate(idUpdate);
            iUserService.updateUser(idUpdate, user1);

            //Tìm kiếm tới user có id muốn sửa
            User foundUser = iUserService.findUserById(idUpdate);
            //Lấy ra username của user muốn update
            String strUsername = foundUser.getUsername();
            //Sửa đổi thông tin người dùng mới update ở file order
            iOrderService.updateOrderByUpadteUser(strUsername, user1.getName(),user1.getPhone());

            System.out.printf("Cập nhật thành công user có id %s\n", idUpdate);
            launcher();
        }else if(AuthUtils.hasRole(ERole.USER)){
            showPersonalInfo();
            System.out.println("---------------------------------------------");
            System.out.println("-         Tùy chọn:                         -");
            System.out.println("-         Nhập 1. Sửa tên                   -");
            System.out.println("-         Nhập 2. Sửa số điện thoại         -");
            System.out.println("-         Nhập 3. Sửa ngày sinh             -");
            System.out.println("-         Nhập 4. Sửa password              -");
            System.out.println("-         Nhập 5. Sửa toàn bộ               -");
            System.out.println("-         Nhập 6. Quay lại                  -");
            System.out.println("---------------------------------------------");
            int actionMenu = GetValue.getIntOfWithBounds(1,6);
            switch (actionMenu){
                case 5:
                    updateAllPersonalInfo();
                    break;
                case 1:
                    updatePersonalName();
                    break;
                case 2:
                    updatePersonalPhone();
                    break;
                case 3:
                    updatePersonalDOB();
                    break;
                case 4:
                    updatePersonalPassword();
                    break;
                case 6:
                    launcher();
                    break;
            }
        }
    }

    private User getUserBasicUpdate(int idUpdate) {
        String strName = checkInputValid(ValidateUtils.FIELD_NAME,ValidateUtils.FIELD_NAME_MASSAGE,ValidateUtils.REGEX_NAME);
        String phone = checkInputValid(ValidateUtils.FIELD_PHONE,ValidateUtils.FIELD_PHONE_MASSAGE,ValidateUtils.REGEX_PHONE);
        LocalDate dob = DateUtils.returnValidDOBUser();

        System.out.println("Nhập giới tính: ");
        for (EGender eGender : EGender.values()) {
            System.out.println(eGender.getName() + " " + eGender.getId());
        }
        int idGender = GetValue.getIntOfWithBounds(1,3);
        EGender gender = EGender.findById(idGender);

        User user = new User(idUpdate,strName,phone,"","",dob,gender,ERole.USER);

        return user;
    }

    private void updatePersonalPassword() {
        String strPassword = checkInputValid(ValidateUtils.FIELD_PASSWORD,ValidateUtils.FIELD_PASSWORD_MESSAGE,ValidateUtils.REGEX_PASSWORD);

        User user = AuthUtils.getUserAuthenticator();
        PasswordUtils.generatePassword(strPassword);
        System.out.println("Cập nhật thông tin cá nhân thành công");
        displayPersonalInfo(user);
        launcher();
    }

    private void updatePersonalDOB() {
        LocalDate dob = DateUtils.returnValidDOBUser();
        User user = AuthUtils.getUserAuthenticator();
        user.setDob(dob);
        System.out.println("Cập nhật thông tin cá nhân thành công");
        displayPersonalInfo(user);
        launcher();
    }

    private void updatePersonalPhone() {
        String phone = checkInputValid(ValidateUtils.FIELD_PHONE,ValidateUtils.FIELD_PHONE_MASSAGE,ValidateUtils.REGEX_PHONE);

        User user = AuthUtils.getUserAuthenticator();
        user.setPhone(phone);
        System.out.println("Cập nhật thông tin cá nhân thành công");
        displayPersonalInfo(user);

        iOrderService.updateOrderByUpadteUser(user.getUsername(), user.getName(), phone);
        launcher();
    }

    private void updatePersonalName() {
        String strName = checkInputValid(ValidateUtils.FIELD_NAME,ValidateUtils.FIELD_NAME_MASSAGE,ValidateUtils.REGEX_NAME);

        User user = AuthUtils.getUserAuthenticator();
        user.setName(strName);
        System.out.println("Cập nhật thông tin cá nhân thành công");
        displayPersonalInfo(user);

        iOrderService.updateOrderByUpadteUser(user.getUsername(), strName, user.getPhone());
        launcher();
    }

    private void updateAllPersonalInfo() {
        String strName = checkInputValid(ValidateUtils.FIELD_NAME,ValidateUtils.FIELD_NAME_MASSAGE,ValidateUtils.REGEX_NAME);
        String phone = checkInputValid(ValidateUtils.FIELD_PHONE,ValidateUtils.FIELD_PHONE_MASSAGE,ValidateUtils.REGEX_PHONE);
        LocalDate dob = DateUtils.returnValidDOBUser();
        String strPassword = checkInputValid(ValidateUtils.FIELD_PASSWORD,ValidateUtils.FIELD_PASSWORD_MESSAGE,ValidateUtils.REGEX_PASSWORD);

        User user = AuthUtils.getUserAuthenticator();
        user.setName(strName);
        user.setPhone(phone);
        user.setDob(dob);
        user.setPassword(PasswordUtils.generatePassword(strPassword));

        iUserService.updateUser(user.getId(), user);
        System.out.println("Cập nhật thông tin cá nhân thành công");
        displayPersonalInfo(user);

        //Cập nhật ở file order
        iOrderService.updateOrderByUpadteUser(user.getUsername(),strName,phone);
        launcher();
    }
    public void addUserByUser(){
        User user = getUserBasicInfo();
        iUserService.createUser(user);
        System.out.println("Đăng kí thành công");
    }
    public void addUser() {
        User user1 = getUserBasicInfo();
        iUserService.createUser(user1);
        System.out.println("Thêm user thành công");

            showUser();
            launcher();
    }
    public void showUser() {
        //2,Phan Văn Tài,02315478542,vantai123,vantai123,25-01-1990,MALE,USER
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("- %5s | %25s | %15s | %15s | %15s | %15s | %10s -\n",
                "ID", "Name", "Phone", "Username","DOB", "Gender","Role");
        List<User> users = iUserService.getAll();
        for (User u : users) {
            System.out.printf("- %5s | %25s | %15s | %15s | %15s | %15s | %10s - \n",
                    u.getId(), u.getName(), u.getPhone(), u.getUsername(), u.getDob(), u.getGender(),u.getRole());
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
    }
    public String  checkInputValid(String fieldName, String  fieldMessage, String fieldPattern){
        String input = null;
        boolean validateInput = false;
        do{
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if(!ValidateUtils.isValid(fieldPattern,input)){
                System.out.println(fieldMessage);
                validateInput = true;
            }else {
                validateInput = false;
            }
        }while (validateInput);
            return input;
    }
    private String checkUsername(List<User> users){
        boolean check = false;
        String strUsername;
        do {
            strUsername = checkInputValid(ValidateUtils.FIELD_USERNAME, ValidateUtils.FIELD_USERNAME_MEESAGE, ValidateUtils.REGEX_USERNAME);
            if (!UsernameUtils.isValidUsername(strUsername, users)) {
                check = false;
                System.out.println("Username đã tồn tại. Vui lòng nhập username khác!");
            } else {
                check = true;

            }
        } while (!check);
        return strUsername;
    }
    public User getUserBasicInfo() {
        List<User> users = iUserService.getAll();
        String  strName = checkInputValid(ValidateUtils.FIELD_NAME, ValidateUtils.FIELD_NAME_MASSAGE, ValidateUtils.REGEX_NAME);
        String phone = checkInputValid(ValidateUtils.FIELD_PHONE, ValidateUtils.FIELD_PHONE_MASSAGE, ValidateUtils.REGEX_PHONE);
        LocalDate dob = DateUtils.returnValidDOBUser();
        String username = checkUsername(users);
        String password = checkInputValid(ValidateUtils.FIELD_PASSWORD, ValidateUtils.FIELD_PASSWORD_MESSAGE, ValidateUtils.REGEX_PASSWORD);

        System.out.println("Nhập giới tính: ");
        for (EGender eGender : EGender.values()) {
            System.out.println(eGender.getName() + ": " + eGender.getId());
        }
        int idGender = GetValue.getIntOfWithBounds(1,3);
        EGender gender = EGender.findById(idGender);


        User user = new User(users.size() + 1, strName, phone, username,
                PasswordUtils.generatePassword(password), dob, gender, ERole.USER);

        return user;
    }
    public static void main(String[] args) {
        UserView userView = new UserView();
        userView.launcher();
    }
}

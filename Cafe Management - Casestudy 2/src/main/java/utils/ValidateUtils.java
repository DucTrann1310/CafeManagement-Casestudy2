package utils;

import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String REGEX_NAME = "^(?! )[A-Za-z\\p{L}\\s]{1,50}$";
    public static final String REGEX_PHONE = "^0[1-9][0-9]{8}$";
    public static final String REGEX_DOB = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-(19|20)\\d{2}$";
    public static final String REGEX_USERNAME = "^[a-zA-Z][a-zA-Z0-9_-]{3,16}$";
    public static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])[a-zA-Z0-9!@#$%^&*()-_=+\\\\|\\\\\\\\[{\\\\]};:'\\\",<.>/?].{8,}$";
    public static final String REGEX_IDORDER = "^\\d{1,5}$";
    public static final String FIELD_IDORDER = "idOrder";
    public static final String FIELD_IDORDER_MESSAGE = "Nhập một số nguyên dương chứa tối đa 5 chữ số";
    public static final String FIELD_NAME = "tên";
    public static final String FIELD_NAME_MASSAGE = "Tên bắt đầu bằng kí tự, có từ 1 - 50 kí tự";
    public static final String FIELD_PHONE = "số điện thoại";
    public static final String FIELD_PHONE_MASSAGE = "Số điện thoại phải bắt đầu bằng số 0 và phải có 10 số";
    public static final String FIELD_DOB = "ngày sinh";
    public static final String FIELD_DOB_MESSAGE = "Ngày sinh không hợp lệ. Vui lòng nhập lại theo định dạng!";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_USERNAME_MEESAGE = "Username không hợp lệ\n" +
            "Username phải bắt đầu bằng kí tự\n" +
            "Có ít nhất 4 kí tự";
    public static final String FIELD_PASSWORD = "mật khẩu";
    public static final String FIELD_PASSWORD_MESSAGE = "Mật khẩu không hợp lệ\n" +
            "Chứa ít nhất 1 chữ số\n" +
            "Chứa ít nhất 1 chữ cái viết thường (a - z)\n" +
            "Không chứa khoảng trắng\n" +
            "Có ít nhất 8 kí tự";
    public static final String REGEX_PRICE = "^\\d{4,}";
    public static final String FIELD_PRICE = "giá";
    public static final String FIELD_PRICE_MESSAGE = "Giá tối thiểu phải 4 chữ số";
//    public static boolean isValidName(String strName){
//        return Pattern.matches(REGEX_NAME, strName);
//    }
//    public static boolean isValidPhone(String strPhone){
//        return Pattern.matches(REGEX_PHONE,strPhone);
//    }
//    public static boolean isValidDOB(String strDOB){
//        return Pattern.matches(REGEX_DOB, strDOB);
//    }
//    public static boolean isValidUsername(String strUsername){
//        return Pattern.matches(REGEX_USERNAME,strUsername);
//    }
//    public static boolean isValidPassword(String strPassword){
//        return Pattern.matches(REGEX_PASSWORD,strPassword);
//    }
    public static boolean isValid(String strPattern, String strInput){
        return Pattern.matches(strPattern,strInput);
    }
}

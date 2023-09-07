package utils;

import java.util.Scanner;

public class GetValue {
    static Scanner input = new Scanner(System.in);
    public static int getIntOfWithBounds(String str,int min, int max) {
        try {
            System.out.println(str);
            int enter = Integer.parseInt(input.nextLine());
            if (enter > max || enter < min ) {
                throw new Exception("Không hợp lệ. Nhập lại giá trị trong khoảng " + min + " - " + max + "\n");
            }
            return enter;
        }  catch (Exception e) {
            System.out.printf("Không hợp lệ. Nhập lại giá trị trong khoảng " + min + " - " + max +"\n");
            return getIntOfWithBounds(str,min,max);
        }
    }
    public static int getIntOfWithBounds(int min, int max){
        try {
            int enter = Integer.parseInt(input.nextLine());
            if (enter > max || enter < min ) {
//                throw new Exception("Number invalid!");
                throw new Exception("Không hợp lệ. Nhập lại giá trị trong khoảng " + min + " - " + max +"\n");
            }
            return enter;
        }  catch (Exception e) {
            System.out.printf("Không hợp lệ. Nhập lại giá trị trong khoảng " + min + " - " + max +"\n");
//            System.out.println("Number invalid");
            return getIntOfWithBounds(min,max);
        }
    }
    public static int getIntOfWithBounds(int min){
        try {
            int enter = Integer.parseInt(input.nextLine());
            if (enter < min ) {
//                throw new Exception("Number invalid!");
                throw new Exception("Không hợp lệ. Giá trị tối thiểu phải là  " + min + "\n");
            }
            return enter;
        }  catch (Exception e) {
            System.out.printf("Không hợp lệ. Giá trị tối thiểu phải là  " + min + "\n");
//            System.out.println("Number invalid");
            return getIntOfWithBounds(min);
        }
    }

    public static String getString(String str) {
        try {
            System.out.println(str);
            String enter = input.nextLine();
            return enter;
        } catch (Exception e) {
            System.out.println("Không hợp lệ! Nhập lại: ");
            return getString(str);
        }
    }

}

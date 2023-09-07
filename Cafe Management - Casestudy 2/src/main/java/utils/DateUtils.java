package utils;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class DateUtils {
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM-yyyy");
    private static DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
    public static LocalDate parseDate(String strDate){
        try {
            return LocalDate.parse(strDate, dateTimeFormatter);
        }catch (DateTimeParseException dateTimeParseException){
            return null;
        }
    }
    public static String formatDate(LocalDate localDate){
        try {
            return dateTimeFormatter.format(localDate);
        }catch (DateTimeParseException dateTimeParseException){
            dateTimeParseException.printStackTrace();
        }
        return null;
    }
    public static YearMonth parseMonth(String strMonth){
        try {
            return YearMonth.parse(strMonth, monthFormatter);
        }catch (DateTimeParseException dateTimeParseException){
            return null;
        }
    }
    public static String formatMonth(LocalDate localDate){
        try {
            return monthFormatter.format(localDate);
        }catch (DateTimeParseException dateTimeParseException){
            dateTimeParseException.printStackTrace();
        }
        return null;
    }
    public static Year parseYear(String strYear){
        try {
            return Year.parse(strYear, yearFormatter);
        }catch (DateTimeParseException dateTimeParseException){
            return null;
        }
    }
    public static String formatYear(LocalDate localDate){
        try {
            return yearFormatter.format(localDate);
        }catch (DateTimeParseException dateTimeParseException){
            dateTimeParseException.printStackTrace();
        }
        return null;
    }
    public static LocalDate returnValidDOBUser(){
        LocalDate dob = null;
        do {
            System.out.println("Nhập ngày sinh (dd-MM-yyyy):");
            String strDOB = scanner.nextLine();

            dob = parseDate(strDOB);
            if(dob == null){
                System.out.println("Ngày không hợp lệ. Vui lòng nhập lại theo định dạng dd-MM-yyyy");
//                returnValidDOBUser();
            }else {
                LocalDate tenYearsAgo = LocalDate.now().minusYears(10);
                LocalDate onehundredYearAgo = LocalDate.now().minusYears(100);
                if(dob.isBefore(tenYearsAgo) && dob.isAfter(onehundredYearAgo)){
                    break;
                }else {
                    System.out.println("Ngày sinh không hợp lệ. Vui lòng nhập ngày sinh có tuổi lớn hơn 10 và nhỏ hơn 100");
                }
            }
        }while (dob == null || dob.isAfter(LocalDate.now().minusYears(10)));
        return dob;
    }
    public static LocalDate getValidDate(){
        LocalDate date;
        do {
            System.out.println("Nhập ngày (dd-MM-yyyy): ");
            String strDate = scanner.nextLine();
            date = getValidDateFromMessage(strDate,"Ngày không hợp lệ. Vui lòng nhập lại theo định dạng dd-MM-yyyy");
        }while (date == null);
        return date;
    }
    public static YearMonth getValidMonth() {
        YearMonth month;
        do {
            System.out.println("Nhập tháng (MM-yyyy): ");
            String strMonth = scanner.nextLine();
            month = getValidMonthFromMessage(strMonth,"Tháng không hợp lệ. Vui lòng nhập lại theo định dạng MM-yyyy");
        } while (month == null);
        return month;
    }
    public static Year getValidYear() {
        Year year;
        do {
            System.out.println("Nhập năm (yyyy): ");
            String strYear = scanner.nextLine();
            year = getValidYearFromMessage(strYear,"Năm không hợp lệ. Vui lòng nhập lại theo định dạng yyyy");
        }while (year == null);
        return year;
    }
    public static LocalDate getValidDateFromMessage(String  strDate, String strMessage){
        LocalDate date = parseDate(strDate);
        if(date == null){
            System.out.println(strMessage);
        }
        return date;
    }
    public static YearMonth getValidMonthFromMessage(String strMonth, String strMessage){
        YearMonth month = parseMonth(strMonth);
        if(month == null){
            System.out.println(strMessage);
        }
        return month;
    }
    public static Year getValidYearFromMessage(String strYear, String strMessage){
        Year year = parseYear(strYear);
        if(year == null){
            System.out.println(strMessage);
        }
        return year;
    }
}

package utils;

import models.IParseModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static  <T> List<T> readData(String fileName, Class<T> clazz){
        List<T> data = new ArrayList<>();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try{
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            String line = null;

            while ((line = bufferedReader.readLine()) != null){
                Object obj = clazz.newInstance();
                IParseModel<T> iParseModel = (IParseModel) obj;
                data.add(iParseModel.parse(line));
            }
        }catch (FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }finally {
            try{
                fileReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return data;
    }

    public static <T> void writeData(String  fileName,List<T> data){
        FileWriter fileWriter = null;
        try{
            File file = new File(fileName);
            fileWriter = new FileWriter(file);

            for(int i = 0; i< data.size();i++){
                fileWriter.write(data.get(i).toString());
                if(i != data.size() - 1){
                    fileWriter.write("\n");
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }finally {
            try{
                fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}

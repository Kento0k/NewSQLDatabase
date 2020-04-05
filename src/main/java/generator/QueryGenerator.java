package generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import structure.DataBase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class QueryGenerator {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try{
            classLoader.getResourceAsStream("structure.yaml");
        }
        catch (NullPointerException e){
            System.out.println("File not found!");
            return;
        }
        InputStream file = classLoader.getResourceAsStream("structure.yaml");
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        Generator generator = new Generator (objectMapper.readValue(file, DataBase.class));
        generator.printInfo();
        try {
            List<String> queryList = generator.generateSelects(args[0], args[1], args[2].equals("true"));
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Please input arguments!");
            return;
        }
        List<String> queryList = generator.generateSelects(args[0], args[1], args[2].equals("true"));
        for(String query : queryList)
            System.out.println(query);
    }
}

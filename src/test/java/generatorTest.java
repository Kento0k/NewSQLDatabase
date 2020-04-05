import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import generator.Generator;
import org.junit.Assert;
import org.junit.Test;
import structure.DataBase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class generatorTest {
    @Test
    public void testGenerateSelects() throws IOException {
        List<String> args = new ArrayList<>();
        List<String> queryList = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream file = classLoader.getResourceAsStream("structure.yaml");
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        Generator generator = new Generator (objectMapper.readValue(file, DataBase.class));
        args.add(".*\\.actor");
        args.add("true");
        args.add("true");
        queryList = generator.generateSelects(args.get(0), args.get(1), args.get(2).equals("true"));
        Assert.assertEquals(1, queryList.size());
        Assert.assertEquals("SELECT * FROM sakila.actor\n" +
                "WHERE first_name LIKE '%true%'\n" +
                "OR last_name LIKE '%true%'\n" +
                "OR has_kids = true", queryList.get(0));
        args.clear();
        queryList.clear();
        args.add(".*\\.actor");
        args.add("2016-02-15");
        args.add("true");
        queryList = generator.generateSelects(args.get(0), args.get(1), args.get(2).equals("true"));
        Assert.assertEquals(1, queryList.size());
        Assert.assertEquals("SELECT * FROM sakila.actor\n" +
                "WHERE first_name LIKE '%2016-02-15%'\n" +
                "OR last_name LIKE '%2016-02-15%'\n" +
                "OR last_update = '2016-02-15'", queryList.get(0));
        args.clear();
        queryList.clear();
        args.add(".*\\.actor");
        args.add("42");
        args.add("false");
        queryList = generator.generateSelects(args.get(0), args.get(1), args.get(2).equals("true"));
        Assert.assertEquals(1, queryList.size());
        Assert.assertEquals("SELECT * FROM sakila.actor\n" +
                "WHERE actor_id = 42\n" +
                "OR first_name ILIKE '%42%'\n" +
                "OR last_name ILIKE '%42%'", queryList.get(0));
    }
}

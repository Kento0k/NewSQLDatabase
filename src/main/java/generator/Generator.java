package generator;


import structure.Column;
import structure.DataBase;
import structure.Schema;
import structure.Table;
import util.Pair;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Generator {
    private DataBase dataBase;

    public Generator(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void printInfo() {
        for (Schema s : dataBase.getSchemas()) {
            System.out.println("Schema: " + s.getName());
            for(Table t : s.getTables()){
                System.out.println("\tTable: " + t.getName());
                System.out.println("\tColumns:");
                for (Column c : t.getColumns()){
                    System.out.println("\t\tName: " + c.getName());
                    System.out.println("\t\tType: "+c.getType());
                }
            }
        }
    }

    public List<String> generateSelects(String tablePattern, String query, Boolean caseSensitive){

        List<String> selectList = new ArrayList<>();
        List<String> tablePath = Arrays.asList(tablePattern.split("\\\\."));
        List<Pair> fromList = getFroms(tablePath);
        for(Pair from : fromList){
            boolean columnFlag = false;
            boolean orFlag = false;
            String select = "SELECT * FROM "
                    .concat(from.getKey())
                    .concat(".")
                    .concat(from.getValue().getName())
                    .concat("\nWHERE ");
            for(Column column : from.getValue().getColumns()){
                if(column.getType().matches("varchar\\([0-9]*\\)")){
                    if(orFlag)
                        select = select.concat("OR ");
                    if(!columnFlag)
                        columnFlag = true;
                    select = select.concat(column.getName());
                    if(caseSensitive)
                        select = select.concat(" LIKE ");
                    else
                        select = select.concat(" ILIKE ");
                    select = select.concat("'%")
                            .concat(query)
                            .concat("%'\n");
                    if(!orFlag)
                        orFlag = true;
                }
                else if(column.getType().equals("integer")){
                    if(query.matches("[0-9]*")){
                        if(orFlag)
                            select = select.concat("OR ");
                        if(!columnFlag)
                            columnFlag = true;
                        select = select.concat(column.getName())
                                .concat(" = ")
                                .concat(query)
                                .concat("\n");
                        if(!orFlag)
                            orFlag = true;
                    }
                }
                else if(column.getType().equals("boolean")){
                    if(query.equals("true") || query.equals("false")){
                        if(orFlag)
                            select = select.concat("OR ");
                        if(!columnFlag)
                            columnFlag = true;
                        select = select.concat(column.getName())
                                .concat(" = ")
                                .concat(query)
                                .concat("\n");
                        if(!orFlag)
                            orFlag = true;
                    }
                }
                else if(column.getType().equals("date")){
                    if(query.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")){
                        List<String> date = Arrays.asList(query.split("-"));
                        try{
                            LocalDate.of(Integer.parseInt(date.get(0)),
                                    Integer.parseInt(date.get(1)),
                                    Integer.parseInt(date.get(2)));
                        }
                        catch (DateTimeException e){
                            continue;
                        }
                        if(orFlag)
                            select = select.concat("OR ");
                        if(!columnFlag)
                            columnFlag = true;
                        select = select.concat(column.getName())
                                .concat(" = ")
                                .concat("'")
                                .concat(query)
                                .concat("'")
                                .concat("\n");
                        if(!orFlag)
                            orFlag = true;
                    }
                }
            }
            if(columnFlag) {
                select = select.substring(0, select.length()-1);
                selectList.add(select);
            }
        }
        return selectList;
    }

    List<Pair> getFroms(List<String> tablePath){
        List<Pair> fromList = new ArrayList<>();
        try {
            Pattern.compile(tablePath.get(0));
        }
        catch (PatternSyntaxException e){
            System.out.println("Wrong table pattern syntax!");
            return fromList;
        }
        try {
            Pattern.compile(tablePath.get(1));
        }
        catch (PatternSyntaxException e){
            System.out.println("Wrong table pattern syntax!");
            return fromList;
        }
        Pattern schemaPattern = Pattern.compile(tablePath.get(0));
        Pattern tablePattern = Pattern.compile(tablePath.get(1));
        for(Schema schema : dataBase.getSchemas()){
            Matcher matcher = schemaPattern.matcher(schema.getName());
            if(matcher.matches()){
                for(Table table : schema.getTables()){
                    matcher = tablePattern.matcher(table.getName());
                    if(matcher.matches())
                        fromList.add(new Pair(schema.getName(), table));
                }
            }
        }
        return fromList;
    }
}

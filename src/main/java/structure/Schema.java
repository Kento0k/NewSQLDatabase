package structure;

import structure.Table;

import java.util.List;

public class Schema {
    private String name;
    private List<Table> tables;

    public Schema(String name, List<Table> tables) {
        this.name = name;
        this.tables = tables;
    }

    public Schema(){}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Table> getTables() { return tables; }
    public void setTables(List<Table> tables) { this.tables = tables; }

}

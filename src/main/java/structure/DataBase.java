package structure;

import structure.Schema;

import java.util.List;

public class DataBase {
    private List<Schema> schemas;

    public DataBase(List<Schema> schemas){
        this.schemas = schemas;
    }

    public DataBase(){};

    public List<Schema> getSchemas() { return schemas; }
    public void setSchemas(List<Schema> schemas) { this.schemas = schemas; }

}


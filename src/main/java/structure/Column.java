package structure;

public class Column {
    private String name;
    private String type;

    public Column(String name, String type){
        this.name = name;
        this.type = type;
    }

    public Column(){}

    public String getName(){ return this.name; }
    public void setName(String name){ this.name = name; }

    public String getType(){ return this.type; }
    public void setType(){ this.type = type; }

}

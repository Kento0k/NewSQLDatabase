package util;

import structure.Table;

public class Pair {
    private String key;
    private Table value;

    public Pair(String key, Table value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Table getValue() {
        return value;
    }

    public void setValue(Table value) {
        this.value = value;
    }
}

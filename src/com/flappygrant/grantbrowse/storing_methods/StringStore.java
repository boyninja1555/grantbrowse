package com.flappygrant.grantbrowse.storing_methods;

public class StringStore {
    private String instance;
    
    public StringStore(String instance) {
        this.instance = instance;
    }
    
    public String get(String key) {
        return instance.split(key + ":")[1].split(";")[0];
    }

    public void set(String key, String value) {
        instance = instance.replace(key + ":" + instance.split(key + ":")[1], key + ":" + value);
    }
}

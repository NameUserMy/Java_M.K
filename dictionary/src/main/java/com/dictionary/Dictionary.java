package com.dictionary;

import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dictionary {
    private Map<String, String> dict;

    public Dictionary() {
        dict = new HashMap<>();
    }

    public String addTranslation(String eng, String ua) {
        if (checkData(eng, ua)) {
            dict.put(eng, ua);
            return "Data added";
        } else {
            return "You need to fill in all the data";
        }
    }

    public String searchWord(String word) {

        for(Map.Entry<String,String> e:dict.entrySet()){
            if(e.getKey().equals(word)){
              return String.format("%s -- %s\n ", e.getKey(), e.getValue());
            }else if(e.getValue().equals(word)){
                return String.format("%s -- %s\n ", e.getValue(),e.getKey());
            }
        }
        return "No result";
    }

    private boolean checkData(String eng, String ua) {

        if (ua.trim().isEmpty() || eng.trim().isEmpty() || eng == null || ua == null) {

            return false;

        } else {

            return true;
        }
    }
}

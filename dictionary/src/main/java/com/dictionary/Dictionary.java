package com.dictionary;

import java.util.HashMap;
import java.util.Map;


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
              return String.format("%s -- %s", e.getKey(), e.getValue());

            }else if(e.getValue().equals(word)){
                return String.format("%s -- %s", e.getValue(),e.getKey());
            }
        }
        return "No result";
    }

    private boolean checkData(String eng, String ua) {
       return (ua.trim().isEmpty() || eng.trim().isEmpty()); 
    }
}

package com.backend.rest;

import java.util.Map;

public class RestResponse {

    private int status;    
    private String message;
    private String resourceUrl;//Resource identification in requests: Individual resources are identified in requests using URIs.
    private Map<String,String> meta;
    private long cashTime;     //seconds
    private Object data;

    public int getStatus() {

        return status;
    }

    public RestResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {

        return message;
    }

    public RestResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getResourceUrl(){


        return resourceUrl;
    }  

    public RestResponse setResourceUrl(String resourceUrl){

        this.resourceUrl=resourceUrl;
        return this;

    }

    public long getCashTime(){

        return cashTime;

    }
   
    public RestResponse setCashTime(long cashTime){

        this.cashTime=cashTime;
        return this;

    }
   
    public Map<String,String> getMeta(){

        return meta;

    }
   
    public RestResponse setMeta(Map<String,String> meta){

        this.meta=meta;
        return this;

    }
    
    public RestResponse setData(Object data){
        this.data=data;
        return this;
    }
    
    public Object getData(){


        return data;

    }

    
}

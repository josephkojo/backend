package com.springDevelopers.Backend.DTO;

public class Counter {
    public  String getCountNumber(Integer keyId){
        String counter = "00"+ Integer.toString(keyId);
        return counter;

    }
}

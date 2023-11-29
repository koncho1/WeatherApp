package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherInfo {

    private String name;
    private float temp;
    private int pressure;
    private int humidity;
    private float wind;

    //Getters & Setters


    public String getName(){ return name;}

    public void setName(String name_){ this.name=name_;}
    public float getTemp(){
        return temp;
    }

    public void setTemp(float temp_){
        this.temp=temp_;
    }

    public int getPressure(){
        return pressure;
    }

    public void setPressure(int pressure_){
        this.pressure=pressure_;
    }

    public int getHumidity(){
        return humidity;
    }

    public void setHumidity(int humidity_){
        this.humidity=humidity_;
    }

    public float getWind(){
        return wind;
    }

    public void setWind(float wind_){
        this.wind=wind_;
    }

    //

    public WeatherInfo(String name_,float temp_,int pressure_, int humidity_,float wind_ ){
        this.name=name_;
        this.temp=temp_;
        this.pressure=pressure_;
        this.humidity=humidity_;
        this.wind=wind_;
    }

    public WeatherInfo(){

    }


}



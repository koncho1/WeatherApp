package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FromCitytoWeatherInfo {

    GetWeatherInfo getinfo;
    public WeatherInfo MakeInfoFromCity(City city) throws JsonProcessingException {
        String json=getinfo.MakeRequest(city);
        JsonNode node = new ObjectMapper().readTree(json);
        WeatherInfo info = new WeatherInfo();
        info.setName(city.getName());
        info.setTemp(node.get("main").get("temp").floatValue());
        info.setHumidity(node.get("main").get("humidity").asInt());
        info.setPressure(node.get("main").get("pressure").asInt());
        info.setWind(node.get("wind").get("speed").floatValue());
        return  info;
    }

    public FromCitytoWeatherInfo(GetWeatherInfo getinfo_){
        this.getinfo=getinfo_;
    }
}

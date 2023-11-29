package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GetWeatherInfo {

    private int statusCode;
    public String MakeRequest(City city) throws JsonProcessingException {
        String url= "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=metric&appid=981c81d5359acb3154a14fcf11a149bf".formatted(city.getLat(),city.getLon());
        //Sends GET request to openweather API
        String result=new String();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpclient.execute(request)) {

            // Get HttpResponse Status
            if(response.getStatusLine().getStatusCode()!=200){
                statusCode=response.getStatusLine().getStatusCode();
                System.out.println("Błąd połączenia kod błędu:%d".formatted(statusCode));
                System.exit(-1);
            }
           //System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            //System.out.println(headers);

            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
                //System.out.println(result);
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  result;
    }

    public int getStatusCode(){
        return statusCode;
    }

    public GetWeatherInfo(){

    }

}

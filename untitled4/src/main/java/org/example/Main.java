package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args)
            throws JsonParseException, JsonMappingException, IOException {
        List<WeatherInfo> weatherInfos= new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.println("P-Podaj miasto, Z-Zakończ");
            String input = scan.nextLine();
            if (input.equals("P")) {
                //Maps the coordinates.json file to a list of Cities
                ObjectMapper mapper = new ObjectMapper();
                File path = new File("src/main/resources/coordinates.json");
                List<City> cities = mapper.readValue(path, new TypeReference<List<City>>() {
                });


                Scanner s = new Scanner(System.in);
                String entered_city = s.nextLine();


                //Finds the chosen city
                List<City> n = cities.stream()
                        .filter(city -> city.getName().equals(entered_city))
                        .collect(Collectors.toList());

                if(n.size()<1){
                    System.out.println("Nie znaleziono miasta");
                    continue;
                }
                City chosen_city = n.get(0);

                WeatherInfo info=new FromCitytoWeatherInfo(new GetWeatherInfo()).MakeInfoFromCity(chosen_city);
                weatherInfos.add(info);

                String print_info = "Temperatura: %.2f Wilgotnosc: %d Cisnienie: %d Wiatr: %.2f".formatted(info.getTemp(), info.getHumidity(), info.getPressure(), info.getWind());
                System.out.println(print_info);
            }
            else if (input.equals("Z")) {
                while(true){
                    System.out.println("P-PDF J-JSON X-XML");
                    String in= scan.nextLine();
                    if (in.equals("P")){
                        savePDF(weatherInfos);
                        System.exit(0);

                    }
                    else if (in.equals("J")) {
                        saveJSON(weatherInfos);
                        System.exit(0);

                    }
                    else if (in.equals("X")) {
                        saveXML(weatherInfos);
                        System.exit(0);

                    }
                    else{
                        System.out.println("Nie rozpoznano komendy");
                    }

                }


            }
            else{
                System.out.println("Nie rozpoznano komendy");
            }
        }
    }


    public static void savePDF(List<WeatherInfo> info)
            throws IOException{
        ObjectMapper map=new ObjectMapper();
        //System.out.println(text);
        PDDocument doc=new PDDocument();
        PDPage page=new PDPage();
        doc.addPage(page);

        PDPageContentStream contentStream=new PDPageContentStream(doc,page);

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD), 12);
        for(int i=0;i<info.size();i++){
            contentStream.beginText();
            String text="Miasto: %s Temperatura: %.2f Wilgotnosc: %d Cisnienie: %d Wiatr: %.2f".formatted(info.get(i).getName(),info.get(i).getTemp(), info.get(i).getHumidity(), info.get(i).getPressure(), info.get(i).getWind());
            contentStream.newLineAtOffset(10,760-i*20);
            contentStream.showText(text);
            contentStream.endText();
        }

        contentStream.close();
        doc.save("results.pdf");
        doc.close();
    }

    public static void saveJSON(List<WeatherInfo> info)
            throws IOException{
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(new File("results.json"),info);
    }

    public static void saveXML(List<WeatherInfo> info)
            throws IOException{
        XmlMapper mapper=new XmlMapper();
        mapper.writeValue(new File("results.xml"),info);
    }

}
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.City;
import org.example.FromCitytoWeatherInfo;
import org.example.GetWeatherInfo;
import org.example.WeatherInfo;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;

public class AppTest {

    GetWeatherInfo getinfo;

    City city1;
    City city2;

    City city3;
    FromCitytoWeatherInfo makeinfo;

    @Before
    public void setUp(){
        getinfo=Mockito.mock(GetWeatherInfo.class);
        city1=createMock("Poznan",52.40f,16.92f);
        city2=createMock("Warszawa",52.15f,21.00f);
        city3=createMock("Berlin",99.00f,13.41f);//Zły lat


    }

    @Test
    public void MockTest1() throws JsonProcessingException {

        Mockito.when(getinfo.MakeRequest(any())).thenReturn("{\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":0.05,\"feels_like\":-4.63,\"temp_min\":-0.98,\"temp_max\":2.82,\"pressure\":1009,\"humidity\":84},\"visibility\":10000,\"wind\":{\"speed\":4.63,\"deg\":280},\"clouds\":{\"all\":0},\"dt\":1701007849,\"sys\":{\"type\":2,\"id\":19661,\"country\":\"PL\",\"sunrise\":1700980303,\"sunset\":1701010058},\"timezone\":3600,\"id\":3088171,\"name\":\"Poznań\",\"cod\":200}");
        FromCitytoWeatherInfo infos=new FromCitytoWeatherInfo(getinfo);
        WeatherInfo info=infos.MakeInfoFromCity(city1);
        assertThat(info.getTemp()).isEqualTo(0.05f);
        assertThat(info.getName()).isEqualTo("Poznan");
        assertThat(info.getWind()).isEqualTo(4.63f);


    }

    @Test
    public void MockTest2() throws JsonProcessingException {

        Mockito.when(getinfo.MakeRequest(any())).thenReturn("{\"weather\":[{\"id\":620,\"main\":\"Snow\",\"description\":\"light shower snow\",\"icon\":\"13n\"}],\"base\":\"stations\",\"main\":{\"temp\":0.03,\"feels_like\":-5.96,\"temp_min\":-0.98,\"temp_max\":1.59,\"pressure\":1002,\"humidity\":76},\"visibility\":10000,\"wind\":{\"speed\":7.2,\"deg\":280},\"snow\":{\"1h\":0.25},\"clouds\":{\"all\":75},\"dt\":1701009507,\"sys\":{\"type\":2,\"id\":2002467,\"country\":\"PL\",\"sunrise\":1700979255,\"sunset\":1701009146},\"timezone\":3600,\"id\":753571,\"name\":\"Zbarz\",\"cod\":200}");
        FromCitytoWeatherInfo infos=new FromCitytoWeatherInfo(getinfo);
        WeatherInfo info=infos.MakeInfoFromCity(city2);
        assertThat(info.getTemp()).isEqualTo(0.03f);
        assertThat(info.getName()).isEqualTo("Warszawa");
        assertThat(info.getWind()).isEqualTo(7.2f);


    }

    @Test
    public void MockTest3() throws JsonProcessingException {
        Mockito.when(getinfo.MakeRequest(any())).thenReturn("{\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"base\":\"stations\",\"main\":{\"temp\":0.1,\"feels_like\":-2.41,\"temp_min\":-1.68,\"temp_max\":1.31,\"pressure\":999,\"humidity\":80},\"visibility\":10000,\"wind\":{\"speed\":2.06,\"deg\":240},\"clouds\":{\"all\":20},\"dt\":1701015871,\"sys\":{\"type\":2,\"id\":2011538,\"country\":\"DE\",\"sunrise\":1700981179,\"sunset\":1701010866},\"timezone\":3600,\"id\":2950159,\"name\":\"Berlin\",\"cod\":200}");
        FromCitytoWeatherInfo infos=new FromCitytoWeatherInfo(getinfo);
        WeatherInfo info=infos.MakeInfoFromCity(city3);
        assertThat(info.getTemp()).isEqualTo(null);
        assertThat(info.getName()).isEqualTo(null);
        assertThat(info.getWind()).isEqualTo(null);
    }

    private City createMock(String name,float lat,float lon){
        City citymock=new City();
        citymock.setName(name);
        citymock.setLat(lat);
        citymock.setLon(lon);
        return citymock;
    }
}

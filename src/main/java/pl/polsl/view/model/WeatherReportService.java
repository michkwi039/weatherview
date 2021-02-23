package pl.polsl.view.model;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.polsl.view.model.WeatherReport;
import pl.polsl.view.model.WeatherReportContainer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class WeatherReportService {

    public ArrayList<WeatherReport> getReportsByDate(String date){
        final String uri = "http://processor:8082/reports/{date}";
//        Date data=new Date();
//        DateFormat formatter;
//        formatter = new SimpleDateFormat("MM/dd/yyyy");
//
//        try {
//            data = formatter.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Map<String, String> params = new HashMap<String, String>();
        date=date.replaceAll("/","-");
        params.put("date", date);

        RestTemplate restTemplate = new RestTemplate();
        WeatherReportContainer result = restTemplate.getForObject(uri, WeatherReportContainer.class, params);

        //System.out.println(result);
        return result.getWeatherReports();
    }

    public ArrayList<WeatherReport> getNextReports(){
        final String uri = "http://processor:8082/next";


        RestTemplate restTemplate = new RestTemplate();
        WeatherReportContainer result = restTemplate.getForObject(uri, WeatherReportContainer.class);

        //System.out.println(result);
        return result.getWeatherReports();
    }
    public ArrayList<WeatherReport> getPreviousReports(){
        final String uri = "http://processor:8082/previous";


        RestTemplate restTemplate = new RestTemplate();
        WeatherReportContainer result = restTemplate.getForObject(uri, WeatherReportContainer.class);

        //System.out.println(result);
        return result.getWeatherReports();
    }
}

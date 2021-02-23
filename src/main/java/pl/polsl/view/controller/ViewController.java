package pl.polsl.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.view.model.WeatherReport;
import pl.polsl.view.model.WeatherReportService;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewController {

    private final WeatherReportService weatherReportService;
    @Autowired
    public ViewController (WeatherReportService weatherReportService){
        this.weatherReportService=weatherReportService;
    }
    @RequestMapping(value = "/mainmenu", method = RequestMethod.GET)
    public String mainMenu(Model model) {

        return "mainmenu";
    }

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String reports(Model model, Optional<String> date,Optional<Boolean> next,Optional<Boolean> previous) {
        List<WeatherReport> result = null;
        Double average = 0d;

        String time;

        if(date.isPresent()){
            time=date.get();
        }else{
            time="12/09/2019";
        }
        if (!next.orElse(false)&&!previous.orElse(false)) {

                result = weatherReportService.getReportsByDate(time);
                for (WeatherReport w : result) {
                    average += w.getTemperature();
                }
                average = average / result.size();
        }else if(next.orElse(false)){
            result =weatherReportService.getNextReports();
            for (WeatherReport w : result) {
                average += w.getTemperature();
            }
            average = average / result.size();
        }else{
            result =weatherReportService.getPreviousReports();
            for (WeatherReport w : result) {
                average += w.getTemperature();
            }
            average = average / result.size();
        }
        if(result==null){
            result=new ArrayList<>();
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("average", average);
        model.addAttribute("node", result);
        model.addAttribute("searchDate", dateFormat.format(result.get(0).getDate()));
        return "reports";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model) {
        return "search";
    }
}

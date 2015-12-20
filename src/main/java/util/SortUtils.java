package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SortUtils {
    public static List<String> playReportUtils(String period, String sort ){
        List<String> res = new LinkedList<>();
        if(period!=null)
            switch (period){
                case "1":
                    res.add("date = CURDATE");
                    break;
                case "2":
                    res.add("date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 WEEK)");
                    break;
                case "3":
                    res.add("date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 MONTH)");
                    break;
            }
        else
            res.add(null);
        if(sort!=null)
            switch (sort){
                case "1":
                    res.add("prices ASC");
                    break;
                case "2":
                    res.add("prices DESC");
                    break;
                case "3":
                    res.add("date ASC");
                    break;
                case "4":
                    res.add("spectacle.name ASC");
                    break;
            }
        else
            res.add(null);
        return res;
    }
}

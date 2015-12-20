package util;

import java.util.Iterator;
import java.util.List;

public class DataToJson {
    public static String queryResToJson(List<List> data){
        String json = "[";
        for (List lst : data) {
            Iterator iter = lst.iterator();
            json += "{";
            while (iter.hasNext()){
                json += "\"" + iter.next()+"\":\""+iter.next()+"\",";
            }
            json = json.substring(0, json.length()-1);
            json += "},";
        }
        json = json.substring(0, json.length()-1);
        json += "]";
        return json;
    }
}

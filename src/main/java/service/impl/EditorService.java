package service.impl;

import dao.EditorDao;
import dao.impl.EditorDaoImpl;
import util.DataToJson;

import java.util.List;

public class EditorService {
    private static EditorDao dao = EditorDaoImpl.getInstance();
    private static EditorService service;

    private EditorService(){}

    public static EditorService getInstance(){
        if(service == null)
            service = new EditorService();
        return service;
    }
    public String performQuery(String query){
        List<List> attrs = dao.performQuery(query);
        return DataToJson.queryResToJson(attrs);
    }
}

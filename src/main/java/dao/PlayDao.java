package dao;

import entity.impl.PlayImpl;
import entity.impl.PlaysDuringSevenDaysImpl;
import entity.impl.PlaysInTheatresImpl;

import java.util.List;

public interface PlayDao extends GenericCRUD<PlayImpl, Long>{
    public List<PlaysInTheatresImpl> getPlaysInTheatres();

    public List<PlaysDuringSevenDaysImpl> getPlaysDuringSevenDays();
}

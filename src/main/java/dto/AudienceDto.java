package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AudienceDto {
    public List<PlaceDto> places = new ArrayList<>();

    public AudienceDto(List<PlaceDto> places) {
        this.places = places;
    }

    public List<PlaceDto> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<PlaceDto> places) {
        this.places = places;
    }
}

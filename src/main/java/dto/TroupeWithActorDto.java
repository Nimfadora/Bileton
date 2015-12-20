package dto;

import java.util.LinkedList;
import java.util.List;

public class TroupeWithActorDto {
    private String troupeName;
    private List<ActorDto> actors = new LinkedList<>();

    public TroupeWithActorDto() {
    }

    public void addActor(ActorDto actorDto){
        if(actorDto!=null && !actors.contains(actorDto))
            actors.add(actorDto);
    }

    public String getTroupeName() {
        return troupeName;
    }

    public void setTroupeName(String troupeName) {
        this.troupeName = troupeName;
    }

    public List<ActorDto> getActors() {
        return actors;
    }

    public List<ActorDto> addActors(List<ActorDto> lst){
        if(lst!=null)
            for(ActorDto actor : lst){
                addActor(actor);
            }
        return actors;
    }
}

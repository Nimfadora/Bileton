package entity.impl;

public class ActorImpl {
    private Long id;
    private String name;
    private Long troupeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTroupeId() {
        return troupeId;
    }

    public void setTroupeId(Long troupeId) {
        this.troupeId = troupeId;
    }
}

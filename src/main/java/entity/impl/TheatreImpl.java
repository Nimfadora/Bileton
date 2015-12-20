package entity.impl;

public class TheatreImpl {
    private String name;
    private Long id;
    private String category = "defalt";

    public String getName() {
        return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

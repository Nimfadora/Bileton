package entity.impl;

public class SpectacleImpl {
    private String name;
    private String summary;
    private Long id;
    private String category = "default";

    public SpectacleImpl() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SpectacleImpl{" +
                "name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", id=" + id +
                '}';
    }
}

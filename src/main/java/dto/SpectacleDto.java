package dto;

public class SpectacleDto {
    private String name;
    private String summary;
    private Long id;
    private String category;

    public SpectacleDto() {
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

    @Override
    public String toString() {
        return "SpectacleDto{" +
                "name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", id=" + id +
                '}';
    }
}

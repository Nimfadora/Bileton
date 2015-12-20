package dto;

public class TicketDto {
    private Long id;
    private Long [] placesId;
    private String email;
    private boolean discount;
    private int reserveType;

    public Long[] getPlacesId() {
        return placesId;
    }

    public void setPlacesId(Long[] placesId) {
        this.placesId = placesId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public int getReserveType() {
        return reserveType;
    }

    public void setReserveType(int reserveType) {
        this.reserveType = reserveType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

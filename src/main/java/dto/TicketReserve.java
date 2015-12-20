package dto;

public class TicketReserve {
    private int [] placesId;
    private String email;
    private String discount;
    private int reserveType;

    public int[] getPlacesId() {
        return placesId;
    }

    public void setPlacesId(int[] placesId) {
        this.placesId = placesId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getReserveType() {
        return reserveType;
    }

    public void setReserveType(int reserveType) {
        this.reserveType = reserveType;
    }
}

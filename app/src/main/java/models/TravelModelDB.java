package models;

public class TravelModelDB {
    private int id;
    private String travelName; // Nome da viagem
    private String description; // Descrição da viagem
    private int numberOfPeople;
    private String departureLocation;
    private String arrivalLocation;
    private String transportationMode;

    public TravelModelDB() {
    }

    public TravelModelDB(String travelName, String description, int numberOfPeople, String departureLocation, String arrivalLocation, String transportationMode) {
        this.travelName = travelName;
        this.description = description;
        this.numberOfPeople = numberOfPeople;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.transportationMode = transportationMode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTravelName() {
        return travelName;
    }

    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public String getTransportationMode() {
        return transportationMode;
    }

    public void setTransportationMode(String transportationMode) {
        this.transportationMode = transportationMode;
    }
}

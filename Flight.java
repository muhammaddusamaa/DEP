public class Flight {
    private int flightId;
    private String flightNumber;
    private String destination;
    private String departureTime;

    // Constructor for new flights (no id)
    public Flight(String flightNumber, String destination, String departureTime) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departureTime = departureTime;
    }

    // Constructor for existing flights (with id)
    public Flight(int flightId, String flightNumber, String destination, String departureTime) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departureTime = departureTime;
    }

    // Getters and Setters
    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}

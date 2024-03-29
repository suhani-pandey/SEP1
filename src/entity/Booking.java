package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


/**
 * A Class that contains the guest,rooms,arrival and departure
 *
 * @author Suhani Pandey
 * @version 1.0
 */

public class Booking implements Serializable {

    private Guest mainBooker;
    private ArrayList<Guest> guests;
    private Room room;
    private int roomNumber;
    private LocalDate arrival;
    private LocalDate departure;
    private boolean isCheckedIn;

    /**
     *  This is a constructor that takes in a guest and a room and creates a booking
     *    object.
     */

    public Booking(Guest mainBooker, Room room) {
        guests = new ArrayList<>();
        this.mainBooker = mainBooker;
        this.room = room;
        roomNumber = room.getRoomNumber();
        guests = new ArrayList<>();

    }

    /**
     * A method to get Arraylist of guest
     * @return guests
     */

    public ArrayList<Guest> getGuests() {
        return guests;
    }

    /**
     * method to get room number
     * @return room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * A method to set guest
     *
     * @param mainBooker takes in Guest
     */

    public void setMainBooker(Guest mainBooker) {
        this.mainBooker = mainBooker;
    }

    /**
     * A method to set rooms
     *
     * @param room takes in Room
     */

    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * A method to set arrival date takes in Date
     *
     * @param arrival
     */

    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    /**
     * A method to set departure date
     *
     * @param departure
     */

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    /**
     * A method to get guest
     *
     * @return guest
     */
    public Guest getMainBooker() {
        return mainBooker;
    }

    /**
     * A method to get rooms
     *
     * @return rooms
     */

    public Room getRoom() {
        return room;
    }

    /**
     * A method to get arrival
     *
     * @return arrival
     */

    public LocalDate getArrival() {
        return arrival;
    }

    /**
     * A method to get departure
     *
     * @return departure
     */

    public LocalDate getDeparture() {
        return departure;
    }

    /**
     * A method to get final check out price in certain date interval
     *
     * @return price
     */

    public double checkOutPrice() {
        int daysStayed = (int) ChronoUnit.DAYS.between(arrival, departure);
        double price = room.getPrice() * daysStayed;
        if (price==0) return room.getPrice();
        return price;

    }



    /**
     * This method adds a list of guests at one time
     *
     * @param guests
     */
    public void addAllGuests(ArrayList<Guest> guests) {
        this.guests.addAll(guests);

    }



    /**
     * @return guest, rooms, arrival, departure
     */

    public String toString() {
        return "Booking{" + "guest=" + mainBooker + ", rooms=" + room
            + ", arrival=" + arrival + ", departure=" + departure + '}';
    }

    /**
     * A method to set checked in guest
     * @param checkedIn takes boolean
     */

    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
    }

    /**
     * A method to get checked in guest
     * @return checked in guest
     */
    public boolean getIsCheckedIn() {
        return isCheckedIn;
    }


    /**
     * A method to check if a given object is an instance of a Booking object
     * If obj is an instance of Booking,turn Object obj into a Booking object
     *
     * @param obj obj a given object
     * @return true if the obj is an instance of Booking, else return false
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) obj;
        return mainBooker.equals(other.mainBooker) && room.equals(other.room)
            && arrival.equals(other.arrival) && departure.equals(other.departure);
    }

    /**
     * A method to get booker name
     * @return firstname of main Booker
     */

    public String getBookerName() {
        return mainBooker.getFirstName();
    }



}

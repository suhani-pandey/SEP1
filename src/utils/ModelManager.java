package utils;

import View.CheckIn.CheckIn;
import entity.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A class for accessing Booking objects and reading/writing to/from files
 *
 * @author
 * @version 1.0
 */
public class ModelManager implements Serializable
{
  private String BOOKING_FILENAME = "booking.bin";
  private String ROOM_FILENAME = "rooms.bin";

  /**
   * A no-argument constructor
   */
  public ModelManager()
  {
  }

  /**
   * Creates an array list BookingList of all Booking objects in the binary file
   *
   * @return array list of every booking object in the binary file
   */
  public BookingList getAllBookings()
  {
    BookingList allBookings = new BookingList();

    try
    {
      allBookings = (BookingList) MyFileHandler.readFromBinaryFile(
          BOOKING_FILENAME);
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }
    catch (IOException e)
    {
      System.out.println("IO Error reading file");
    }
    catch (ClassNotFoundException e)
    {
      System.out.println("Class Not Found");
    }
    return allBookings;
  }

  /**
   * Gets a Booking object which contains phoneNum from the list.
   *
   * @param phoneNum the phone number of the guest whose booking is needed
   * @return the Booking which contains phoneNum, else null
   */
  public BookingList getBookingByGuestPhoneNum(String phoneNum)
  {
    BookingList bookingByGuest = new BookingList();
    BookingList allBookings = getAllBookings();

    for (int i = 0; i < allBookings.size(); i++)
    {
      if ((allBookings.get(i).getMainBooker().getPhoneNumber()
          .equals(phoneNum)))
      {
        bookingByGuest.add(allBookings.get(i));

      }
    }
    return bookingByGuest;
  }

  // not sure
  public RoomList getAllRooms()
  {
    RoomList allRooms = new RoomList();

    try
    {

      allRooms = (RoomList) MyFileHandler.readFromBinaryFile(ROOM_FILENAME);

    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
    catch (ClassNotFoundException e)
    {
      System.out.println("Class Not Found");
    }
    return allRooms;
  }

  /**
   * Creates array list only containing Room objects with a isAvailable()=True value.
   *
   * @return array list of isAvailable Room objects
   */
  public RoomList getAllAvailableRooms()
  {
    RoomList availableRooms = new RoomList();
    RoomList allRooms = getAllRooms();

    for (int i = 0; i < allRooms.size(); i++)
    {
      if (allRooms.get(i).IsAvailable())
      {
        availableRooms.add(allRooms.get(i));
      }
    }

    return availableRooms;
  }

  /**
   * Adds a Booking to the binary file.
   *
   * @param booking the Booking object which will be added to the binary file
   */
  public void addBooking(Booking booking)
  {
    BookingList allBookings = getAllBookings();
    allBookings.addBooking(booking);
    saveBookings(allBookings);
  }

  /**
   * Adds an array list of Booking objects to the binary file.
   *
   * @param bookings the BookingList array list which will be added to the binary file
   */
  public void addBookings(BookingList bookings)
  {
    BookingList allBookings = getAllBookings();
    allBookings.addAllBookings(bookings.getBookings());
    saveBookings(allBookings);

  }


  public void deleteBooking(String firstName,String lastName,String phoneNumber)
  {
    BookingList allBookings = getAllBookings();
    for (int i = 0; i < allBookings.size(); i++)
    {
      Booking booking=allBookings.get(i);
      if (booking.getMainBooker().getFirstName().equals(firstName) && booking.getMainBooker().getLastName().equals(lastName)
          && booking.getMainBooker().getPhoneNumber().equals(phoneNumber))
      {
        allBookings.removeBooking(booking);
      }
    }
    saveBookings(allBookings);

  }

  /**
   * Updates the binary file will newly added or removed Booking objects.
   *
   * @param bookings the array list which contains all Booking objects
   */
  public void saveBookings(BookingList bookings)
  {
    try
    {
      MyFileHandler.writeToBinaryFile(BOOKING_FILENAME, bookings);
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }
    catch (IOException e)
    {
      System.out.println("IO Error writing to file");
    }
  }

  public void saveRoomList(RoomList roomList)
  {
    try
    {
      MyFileHandler.writeToBinaryFile(ROOM_FILENAME, roomList);

    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }
    catch (IOException e)
    {
      System.out.println("IO Error writing to file");
    }
  }

  public void addGuestsToBooking(Booking booking, ArrayList<Guest> guests)
  {
    BookingList allBookings = getAllBookings(); // happens 1 time
    for (int i = 0; i < allBookings.size(); i++) // happens n times
    {
      if (allBookings.get(i).equals(booking)) // Comparison happens 1 time per iteration
      {
        allBookings.get(i).addAllGuests(guests); // getting object at given index happens 1 time
        break; //breaks the loop happens only 1 time
      }
    }
    saveBookings(allBookings); //saves the booking to the booking list, happens 1 time
    // T(0) = 1 + n + 1 + 1 + 1
    // removing all constants
    // O(n)
    // We chose this code to analyze because of its relative complexity
    // compared to other loops in this program.
  }

  public void addAllRooms(ArrayList<Room> rooms)
  {
    RoomList allRooms = getAllRooms();
    allRooms.addAll(rooms);
    saveRoomList(allRooms);
  }

  // searching room
  public RoomList searchRooms(LocalDate arrival, LocalDate departure,
      Room.RoomType roomType, boolean isSmoking)
  {
    // setting the temp list with all rooms
    RoomList allRooms = getAllRooms();
    BookingList allBookings = getAllBookings();
    RoomList roomList = new RoomList();

    for (Room room : allRooms.getRooms())
    {
      if (!(room.getRoomType().equals(roomType)))
        continue;  // do not add when roomtype dont match
      if (!(room.isSmokingAllowed() == isSmoking))
        continue;  // do not add when roomtype match
      roomList.add(room);
    }

    for (Booking booking : allBookings.getBookings())
    {
      LocalDate arrivalDate = booking.getArrival();
      LocalDate departureDate = booking.getDeparture();

      // the room is not booked in either of the two given condition
      // so we remove the room if neither is satisfied

      if (!(arrival.isBefore(arrivalDate) && departure.isBefore(arrivalDate)))
      {
        if (!(departure.isAfter(departureDate) && arrival.isAfter(
            departureDate)))
        {
          roomList.getRooms().remove(booking.getRoom());
        }
      }
    }
    return roomList;
  }

  public ArrayList<Booking> searchBooking(String firstname, String lastname,
      String phoneNumberText)
  {
    BookingList allBookings = getAllBookings();
    ArrayList<Booking> bookings = new ArrayList<>();
    for (Booking booking : allBookings.getBookings())
    {
      Guest guest = booking.getMainBooker();
      if (!guest.getFirstName().equals(firstname))
        continue;
      if (!guest.getLastName().equals(lastname))
        continue;
      if (!guest.getPhoneNumber().equals(phoneNumberText))
        continue;
      bookings.add(booking);
    }
    return bookings;
  }


}

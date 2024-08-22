package org.example;

import org.example.dao.TaxiBookingDao;
import org.example.dao.model.TaxiBooking;
import org.example.dao.model.TaxiBookingDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {

        TaxiBookingDao taxiBookingDao = null;
        // Create table taxi_booking
        Connection con = DBConnector.getConnection();
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS taxi_booking ("
                    + "id INT(5) NOT NULL, "
                    + "taxi_id INT(5) NOT NULL, "
                    + "user_id INT(5) NOT NULL, "
                    + "booking_created_at TIMESTAMP NOT NULL, "
                    + "booking_amount DECIMAL NOT NULL, "
                    + "PRIMARY KEY (id))";
            PreparedStatement ps = con.prepareStatement(createTableSQL);
            ps.execute();
            taxiBookingDao = new TaxiBookingDaoImpl(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //TaxiBookingDao taxiBookingDao = new TaxiBookingDaoImpl();
        TaxiBooking taxiBooking1 = TaxiBooking.builder()
                .id(1)
                .taxiId(1)
                .userId(1)
                .bookingCreatedAt(Instant.now())
                .bookingAmount(1000.0)
                .build();
        TaxiBooking taxiBooking2 = TaxiBooking.builder()
                .id(2)
                .taxiId(2)
                .userId(2)
                .bookingCreatedAt(Instant.now())
                .bookingAmount(1000.0)
                .build();
        taxiBookingDao.createBooking(taxiBooking1);
        taxiBookingDao.createBooking(taxiBooking2);
        System.out.println("Trying to fetch all bookings: ");
        System.out.println(taxiBookingDao.getAllBooking());
        System.out.println("Fetched all bookings: ");


        System.out.println("Fetched booking with id 1: " + taxiBookingDao.getBookingById(1));

        TaxiBooking updatedTaxiBooking1 = TaxiBooking.builder()
                .id(1)
                .taxiId(1)
                .userId(1)
                .bookingCreatedAt(Instant.now())
                .bookingAmount(1200.0)
                .build();
        taxiBookingDao.updateBooking(updatedTaxiBooking1, 1);
        System.out.println("Fetched booking with id 1 after update: " + taxiBookingDao.getBookingById(1));

        System.out.println("Deleting booking with id 1");
        taxiBookingDao.deleteBooking(1);
        System.out.println("After Deleting fetching all booking");

        System.out.println(taxiBookingDao.getAllBooking());

    }
}
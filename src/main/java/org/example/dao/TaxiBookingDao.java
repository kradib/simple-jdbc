package org.example.dao;

import org.example.dao.model.TaxiBooking;

import java.util.List;

public interface TaxiBookingDao {

    int createBooking(TaxiBooking taxiBooking);

    List<TaxiBooking> getAllBooking();
    TaxiBooking getBookingById(int id);

    int updateBooking(TaxiBooking updatedBooking, int id);

    int deleteBooking(int id);
}

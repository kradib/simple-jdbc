package org.example.dao.model;

import org.example.DBConnector;
import org.example.dao.TaxiBookingDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaxiBookingDaoImpl implements TaxiBookingDao {

    private Connection con;

    public TaxiBookingDaoImpl(final Connection con) {
        this.con = con;
    }

    @Override
    public int createBooking(TaxiBooking taxiBooking) {
        try {
            String sql = "insert into taxi_booking(id, taxi_id, user_id, booking_created_at, booking_amount) values(?, ?, ?,?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, taxiBooking.getId());
            ps.setInt(2, taxiBooking.getTaxiId());
            ps.setInt(3, taxiBooking.getUserId());
            ps.setTimestamp(4, Timestamp.from(taxiBooking.getBookingCreatedAt()));
            ps.setDouble(5, taxiBooking.getBookingAmount());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TaxiBooking> getAllBooking() {
        try {
            String selectSQL = "SELECT * FROM taxi_booking";
            PreparedStatement ps = con.prepareStatement(selectSQL);

            ResultSet resultSet = ps.executeQuery();

            List<TaxiBooking> taxiBookings = new ArrayList<>();
            // Process the results
            while (resultSet.next()) {
                taxiBookings.add(TaxiBooking.builder()
                        .id(resultSet.getInt("id"))
                        .taxiId(resultSet.getInt("taxi_id"))
                        .userId(resultSet.getInt("user_id"))
                        .bookingCreatedAt(resultSet.getTimestamp("booking_created_at").toInstant())
                        .bookingAmount(resultSet.getDouble("booking_amount"))
                        .build());
            }
            return taxiBookings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public TaxiBooking getBookingById(int id) {
        try {
            String selectSQL = "SELECT * FROM taxi_booking where id = ?";
            PreparedStatement ps = con.prepareStatement(selectSQL);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if(!resultSet.next()) {
                System.out.println("Could Not find any booking with this Id");
                return null;
            }

            // Process the result
            return TaxiBooking.builder()
                    .id(resultSet.getInt("id"))
                    .taxiId(resultSet.getInt("taxi_id"))
                    .userId(resultSet.getInt("user_id"))
                    .bookingCreatedAt(resultSet.getTimestamp("booking_created_at").toInstant())
                    .bookingAmount(resultSet.getDouble("booking_amount"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int updateBooking(TaxiBooking updatedBooking, int id) {
        try {
            String selectSQL = "UPDATE taxi_booking SET taxi_id = ?, user_id = ?, booking_created_at = ?, booking_amount = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(selectSQL);

            ps.setInt(1, updatedBooking.getTaxiId());
            ps.setInt(2, updatedBooking.getUserId());
            ps.setTimestamp(3, Timestamp.from(updatedBooking.getBookingCreatedAt()));
            ps.setDouble(4, updatedBooking.getBookingAmount());
            ps.setInt(5, id);

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int deleteBooking(int id) {

        try {
            String selectSQL = "DELETE FROM taxi_booking where id = ?";
            PreparedStatement ps = con.prepareStatement(selectSQL);
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

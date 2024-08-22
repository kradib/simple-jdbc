package org.example.dao.model;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;


@Data
@Builder
public class TaxiBooking {

    private int id;
    private int taxiId;
    private int userId;
    private Instant bookingCreatedAt;
    private Double bookingAmount;

}

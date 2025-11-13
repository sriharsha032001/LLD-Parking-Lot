package com.parkingLot.Parking_lot_LLD.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExitResponse {

    private UUID ticketId;
    private Instant exitTime;
    private int amountPaid;
    private String status;
}

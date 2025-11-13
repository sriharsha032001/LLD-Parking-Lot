package com.parkingLot.Parking_lot_LLD.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntryResponse {

    private UUID ticketId;
    private String vehicleType;
    private UUID slotId;

    private int currency;

    private Instant entryTime;
    
}

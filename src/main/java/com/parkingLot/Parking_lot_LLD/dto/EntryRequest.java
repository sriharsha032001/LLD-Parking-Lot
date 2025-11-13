package com.parkingLot.Parking_lot_LLD.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EntryRequest {
    
        @NotBlank(message = "Vehicle number cannot be blank")
        private Long vehicleNumber;

        @NotBlank(message = "Vehicle type cannot be blank")
        private String vehicleType;
    
}

package com.parkingLot.Parking_lot_LLD.Entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parking_slot" , indexes = {@Index(name = "idx_slot_type_Available", columnList = "vehicle_type, is_occupied"),
        @Index(name = "idx_floor_code", columnList = "slot_code, floor_number")})
                
            
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingSlot {

    @Id
    @Column(name = "slot_id", nullable = false, unique = true)
    private UUID slotId;

    @Column(name = "slot_code", nullable = false)
    private String slotCode;

    @Column(name = "floor_number", nullable = false)
    private long floorNumber;

    @Column(name = "is_occupied", nullable = false)
    private Boolean isOccupied;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;


    // Factory method to create ParkingSlot
    public static ParkingSlot createParkingSlot(String slotCode, long floorNumber, VehicleType vehicleType) {
        return ParkingSlot.builder()
                .slotId(UUID.randomUUID())
                .slotCode(slotCode)
                .floorNumber(floorNumber)
                .isOccupied(false)
                .vehicleType(vehicleType)
                .build();
    }
}

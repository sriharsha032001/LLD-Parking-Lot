package com.parkingLot.Parking_lot_LLD.Repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parkingLot.Parking_lot_LLD.Entity.ParkingSlot;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

    // to make sure that when multiple transactions are trying to get the available slot for same vehicle type,
    // only one transaction gets the slot and others wait for the lock to be released

    @Query( value = 
        "SELECT * FROM parking_slot" +
        " WHERE vehicle_type =  :type AND is_occupied = false" +
        " ORDER BY floor_number, slot_id " +
        " LIMIT 1 FOR UPDATE SKIP LOCKED",
        nativeQuery = true)

        // this will return empty if no slot is available for the given vehicle type
        Optional<ParkingSlot> findAvailableSlotByType(@Param("type") String type);

        @Modifying
        @Query("UPDATE ParkingSlot p SET p.isOccupied = :occupied WHERE p.slotId = :slotId")
        int updateOccupiedFlag(@Param("slotId") UUID slotId, @Param("occupied") boolean occupied);
    
}

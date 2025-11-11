package com.parkingLot.Parking_lot_LLD.Repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkingLot.Parking_lot_LLD.Entity.TicketEntity;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
    
}

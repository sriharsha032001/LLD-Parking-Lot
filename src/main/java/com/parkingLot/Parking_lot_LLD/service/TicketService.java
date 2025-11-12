package com.parkingLot.Parking_lot_LLD.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.annotations.NotFound;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.parkingLot.Parking_lot_LLD.Entity.ParkingSlot;
import com.parkingLot.Parking_lot_LLD.Entity.TicketEntity;
import com.parkingLot.Parking_lot_LLD.Entity.VehicleType;
import com.parkingLot.Parking_lot_LLD.Repo.ParkingSlotRepository;
import com.parkingLot.Parking_lot_LLD.Repo.TicketRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final TicketRepository ticketRepository;
    
    public TicketService(ParkingSlotRepository parkingSlotRepository, TicketRepository ticketRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public TicketEntity createTicket(Long vehicleNumber, VehicleType vehicleType) {

        if(vehicleNumber == null || vehicleType == null ){
            throw new IllegalArgumentException("Please provide required details");
        }

        Optional<TicketEntity> openTicket = ticketRepository.findByAlreadyAvailableTicket(vehicleNumber);
        if(openTicket.isPresent()){
            throw new AlreadyParkedException("Vehicle is already parked in the lot");
        }

        ParkingSlot slot = parkingSlotRepository.findAvailableSlotByType(vehicleType.name()).orElseThrow(() -> new noAvailableSlotException("No slots available"));

        slot.setIsOccupied(true);
        parkingSlotRepository.save(slot);



        // create a ticket 

        TicketEntity ticket = TicketEntity.builder().ticketId(UUID.randomUUID()).vehicleNumber(vehicleNumber).vehicleType(vehicleType).entryTime(Instant.now()).paid(false).build();

        ticketRepository.save(ticket);

        return ticket;
    }

    @Transactional
    public TicketEntity closeTicket(UUID ticketId , int price) {
        if(ticketId == null) {
            System.out.println("Ticket is not exists");
        }

        TicketEntity ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException("ticket not found"));

        if(ticket.getExitTime() != null) {
            throw new IllegalStateException("No vehicle found");
        }

        ticket.setExitTime(Instant.now());
        ticket.setPaid(true);
        ticket.setPrice(price);
        ticketRepository.save(ticket);

        if (ticket.getSlotId() != null) {
            
            parkingSlotRepository.updateOccupiedFlag(ticket.getSlotId(), false);
            
        }

        return ticket;

    }



        
}

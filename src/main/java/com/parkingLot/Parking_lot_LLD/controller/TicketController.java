package com.parkingLot.Parking_lot_LLD.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkingLot.Parking_lot_LLD.Entity.TicketEntity;
import com.parkingLot.Parking_lot_LLD.Entity.VehicleType;
import com.parkingLot.Parking_lot_LLD.dto.EntryRequest;
import com.parkingLot.Parking_lot_LLD.dto.EntryResponse;
import com.parkingLot.Parking_lot_LLD.dto.ExitRequest;
import com.parkingLot.Parking_lot_LLD.dto.ExitResponse;
import com.parkingLot.Parking_lot_LLD.service.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/entry")
    public ResponseEntity<EntryResponse> createTicket(@Valid @RequestBody EntryRequest entryRequest) {

        VehicleType vehicleType = VehicleType.valueOf(entryRequest.getVehicleType().toUpperCase().trim());

        TicketEntity ticket = ticketService.createTicket(entryRequest.getVehicleNumber(), vehicleType);

        EntryResponse response = EntryResponse.builder()
                .ticketId(ticket.getTicketId())
                .vehicleType(ticket.getVehicleType().name())
                .slotId(UUID.randomUUID()) // Placeholder for slotId, replace with actual slotId if available
                .currency(0) // Assuming currency is 0 for now
                .entryTime(ticket.getEntryTime())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

        
    }

    public ResponseEntity<ExitResponse> closeTicket(@Valid @RequestBody ExitRequest exitRequest) {

        TicketEntity ticket = ticketService.closeTicket(exitRequest.getTicketId(), 0);

        ExitResponse response = ExitResponse.builder()
                                .ticketId(ticket.getTicketId())
                                .amountPaid(ticket.getPrice())
                                .exitTime(ticket.getExitTime())
                                .status("Closed")
                                .build();

        return ResponseEntity.ok().body(response);

    }
    
}

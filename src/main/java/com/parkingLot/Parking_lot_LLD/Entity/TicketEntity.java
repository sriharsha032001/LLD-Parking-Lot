package com.parkingLot.Parking_lot_LLD.Entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ticket")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketEntity {

    @Id
    @Column(name = "ticket_id", nullable = false, unique = true)
    private UUID ticketId;

    @Column(name = "vehicle_Number", nullable = false)
    private Long vehicleNumber;

    @Column(name = "vehicle_Type", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "slot_id", nullable = false)
    private Long slotId;
    @Column(name = "entry_time", nullable = false)
    private Instant entryTime;

    @Column(name = "exit_time", nullable = true)
    private Instant exitTime;

    @Column(name = "paid", nullable = false)
    private boolean paid;

    @Column(name = "price", nullable = true)
    private Integer price;

    public static TicketEntity createTicket(Long vehicleNumber, VehicleType vehicleType, long slotId) {
        return TicketEntity.builder()
                .ticketId(UUID.randomUUID())
                .vehicleNumber(vehicleNumber)
                .vehicleType(vehicleType)
                .slotId(slotId)
                .entryTime(Instant.now())
                .paid(false)
                .build();
    }

}

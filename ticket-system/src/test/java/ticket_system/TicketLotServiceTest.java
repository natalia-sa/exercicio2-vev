package ticket_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ticket_system.models.Concert;
import ticket_system.models.Ticket;
import ticket_system.models.TicketLot;
import ticket_system.service.ConcertService;
import ticket_system.service.TicketLotService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketLotServiceTest {
    private TicketLotService ticketLotService;

    @BeforeEach
    void setup() {
        this.ticketLotService = new TicketLotService();
    }

    @Test
    void shouldCreateConcert() {
        int id = 1;
        List<Ticket> tickets = new ArrayList<Ticket>();
        double applicableDiscount = 0.5;

        TicketLot expectedTicketLot = new TicketLot(id, tickets, applicableDiscount);

        TicketLot ticketLot = this.ticketLotService.create(id, tickets, applicableDiscount);

        assertEquals(ticketLot, expectedTicketLot);
    }


}

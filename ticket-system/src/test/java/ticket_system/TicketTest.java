package ticket_system;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ticket_system.models.Ticket;
import ticket_system.models.TicketStatus;
import ticket_system.models.TicketType;
import ticket_system.ticket.controllers.TicketController;


class TicketTest {
    private Ticket notSoldTicket;

    @BeforeEach
    void setup() {

        this.notSoldTicket = new Ticket(1, TicketType.VIP, 20.0);

    }

    @Test
    void shouldCreateTicket() {
        int id = 1;
        TicketType ticketType = TicketType.MEIA_ENTRADA;
        double price = 20.0;

        Ticket expectedTicket = new Ticket(id, ticketType, price);

        TicketController ticketController = new TicketController();

        Ticket ticket = ticketController.create(id, ticketType, price );

        assertEquals(expectedTicket, ticket);

    }

    @Test
    void shouldUpdateTicketStatusWhenSellTicket() {
        assertEquals(TicketStatus.NOT_SOLD, this.notSoldTicket.getTicketStatus());

        this.notSoldTicket.sell();

        assertEquals(TicketStatus.SOLD, this.notSoldTicket.getTicketStatus());

    }
}
package ticket_system;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class TicketTest {

    @Test
    void shouldCreateTicket() {
        int id = 1;
        TicketType ticketType = TicketType.MEIA_ENTRADA;
        TicketStatus ticketStatus = TicketStatus.NOT_SOLD;
        double price = 20.0;

        Ticket expectedTicket = new Ticket(id, ticketType, ticketStatus, price);

        TicketController ticketController = new TicketController();

        Ticket ticket = ticketController.create();

        assertEquals(expectedTicket, ticket);

    }
}
package ticket_system;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class TicketTest {

    @Test
    void shouldCreateTicket() {
        int id = 1;
        TicketType ticketType = TicketType.MEIA_ENTRADA;
        boolean isSold = false;

        Ticket expectedTicket = new Ticket(id, ticketType, isSold);

        TicketController ticketController = new TicketController();

        Ticket ticket = ticketController.create();

        assertEquals(expectedTicket, ticket);



    }
}
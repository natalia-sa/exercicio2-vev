package ticket_system;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ticket_system.models.Ticket;
import ticket_system.models.TicketStatus;
import ticket_system.models.TicketType;
import ticket_system.ticket.controllers.TicketController;


class TicketTest {

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


}
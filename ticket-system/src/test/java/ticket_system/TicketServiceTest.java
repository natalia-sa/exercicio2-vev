package ticket_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ticket_system.exceptions.TicketAlreadySoldException;
import ticket_system.models.Ticket;
import ticket_system.models.TicketStatus;
import ticket_system.models.TicketType;
import ticket_system.service.TicketService;


class TicketServiceTest {
    private Ticket notSoldTicket;
    private TicketService ticketService;

    @BeforeEach
    void setup() {

        this.notSoldTicket = new Ticket(1, TicketType.VIP, 20.0);
        this.ticketService = new TicketService();

    }

    @Test
    void shouldCreateTicket() {
        int id = 1;
        TicketType ticketType = TicketType.MEIA_ENTRADA;
        double price = 20.0;
        Ticket expectedTicket = new Ticket(id, ticketType, price);

        Ticket ticket = this.ticketService.create(id, ticketType, price );

        assertEquals(expectedTicket, ticket);

    }


    @Test
    void shouldUpdateTicketStatusWhenSellTicket() throws TicketAlreadySoldException {
        assertEquals(TicketStatus.NOT_SOLD, this.notSoldTicket.getTicketStatus());

        this.ticketService.sellTicket(this.notSoldTicket);

        assertEquals(TicketStatus.SOLD, this.notSoldTicket.getTicketStatus());

    }

    @Test
    void shouldThrowExceptionWhenTryToSellATicketThatAlreadySold() throws TicketAlreadySoldException {
        this.notSoldTicket.sell();
        assertThrows(TicketAlreadySoldException.class, ()->{
            this.ticketService.sellTicket(this.notSoldTicket);
        });
    }
}
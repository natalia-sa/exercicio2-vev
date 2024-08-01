package ticket_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ticket_system.exceptions.TicketLotConfigurationException;
import ticket_system.models.Ticket;
import ticket_system.models.TicketLot;
import ticket_system.models.TicketType;
import ticket_system.service.TicketLotService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicketLotServiceTest {
    private TicketLotService ticketLotService;
    private Ticket vipTicket;
    private Ticket meiaEntradaTicket;

    @BeforeEach
    void setup() {
        this.ticketLotService = new TicketLotService();
    }

    @Test
    void shouldCreateConcert() throws TicketLotConfigurationException {
        int id = 1;
        List<Ticket> tickets = new ArrayList<Ticket>();
        double applicableDiscount = 0.5;

        TicketLot expectedTicketLot = new TicketLot(id, tickets, applicableDiscount);

        TicketLot ticketLot = this.ticketLotService.create(id, tickets, applicableDiscount);

        assertEquals(ticketLot, expectedTicketLot);
    }

    @Test
    void maxDiscountShouldBe25Percent() throws TicketLotConfigurationException {
        int id = 1;
        List<Ticket> tickets = new ArrayList<Ticket>();
        double applicableDiscount = 0.5;

        TicketLot ticketLot = this.ticketLotService.create(id, tickets, applicableDiscount);

        assertEquals(0.25, ticketLot.getApplicableDiscount());
    }

    @Test
    void shouldThrowExceptionCaseVipTicketsIsLowerThan20PercentOfTotalTicket() {
        // 19 Vip Tickets, 10 Meia entrada tickets, 71 normal tickets. Total of 100 tickets and vip tickets are 19%
        List<Ticket> vipTickets = this.generateTickets(19, TicketType.VIP); // 19 Vip tickets
        List<Ticket> meiaEntradaTickets = this.generateTickets(10, TicketType.MEIA_ENTRADA); // 10 meia entrada tickets
        List<Ticket> normalTickets = this.generateTickets(71, TicketType.NORMAL); // 71 normal tickets

        List<Ticket> allTickets = new ArrayList<>();

        allTickets.addAll(vipTickets);
        allTickets.addAll(meiaEntradaTickets);
        allTickets.addAll(normalTickets);

        int id = 1;
        double applicableDiscount = 0.1;

        assertThrows(TicketLotConfigurationException.class, ()->{
            this.ticketLotService.create(id, allTickets, applicableDiscount);
        });

    }

    @Test
    void shouldThrowExceptionCaseVipTicketsIsMoreThan30PercentOfTotalTickets() {
        // 31 Vip Tickets, 10 Meia entrada tickets, 59 normal tickets. Total of 100 tickets and vip tickets are 31%
        List<Ticket> vipTickets = this.generateTickets(31, TicketType.VIP); // 19 Vip tickets
        List<Ticket> meiaEntradaTickets = this.generateTickets(10, TicketType.MEIA_ENTRADA); // 10 meia entrada tickets
        List<Ticket> normalTickets = this.generateTickets(59, TicketType.NORMAL); // 59 normal tickets

        List<Ticket> allTickets = new ArrayList<>();

        allTickets.addAll(vipTickets);
        allTickets.addAll(meiaEntradaTickets);
        allTickets.addAll(normalTickets);

        int id = 1;
        double applicableDiscount = 0.1;

        assertThrows(TicketLotConfigurationException.class, ()->{
            this.ticketLotService.create(id, allTickets, applicableDiscount);
        });

    }

    @Test
    void shouldThrowExceptionCaseMeiaEntradaTicketsAreMoreThan10PercentOfTotalTickets() throws TicketLotConfigurationException {
        // 30 Vip Tickets, 11 Meia entrada tickets, 59 normal tickets. Total of 100 tickets and meia entrada tickets are 11%
        List<Ticket> vipTickets = this.generateTickets(30, TicketType.VIP); // 19 Vip tickets
        List<Ticket> meiaEntradaTickets = this.generateTickets(11, TicketType.MEIA_ENTRADA); // 10 meia entrada tickets
        List<Ticket> normalTickets = this.generateTickets(59, TicketType.NORMAL); // 59 normal tickets

        List<Ticket> allTickets = new ArrayList<>();

        allTickets.addAll(vipTickets);
        allTickets.addAll(meiaEntradaTickets);
        allTickets.addAll(normalTickets);

        int id = 1;
        double applicableDiscount = 0.1;
        assertThrows(TicketLotConfigurationException.class, ()->{
            this.ticketLotService.create(id, allTickets, applicableDiscount);
        });


    }

    private List<Ticket> generateTickets(int quantity, TicketType type) {
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < quantity ; i++) {
            tickets.add(new Ticket(i, type, 20));
        }

        return tickets;
    }


}

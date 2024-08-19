package ticket_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ticket_system.exceptions.TicketLotConfigurationException;
import ticket_system.models.Ticket;
import ticket_system.models.TicketLot;
import ticket_system.models.TicketType;
import ticket_system.service.TicketLotService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @ParameterizedTest
    @ValueSource(doubles = {0, 0.25, 0.1, 0.24, 0.17})
    void shouldCreateLotWithAcceptableDiscounts(double discount) throws TicketLotConfigurationException {
        int id = 1;
        List<Ticket> tickets = new ArrayList<>();

        TicketLot ticketLot = this.ticketLotService.create(id, tickets, discount);

        assertEquals(discount, ticketLot.getApplicableDiscount());
    }

    @Test
    void testShouldReturnMaxDiscount() throws TicketLotConfigurationException {
        int id = 1;
        List<Ticket> tickets = new ArrayList<>();
        double applicableDiscount = 0.5;

        TicketLot ticketLot = this.ticketLotService.create(id, tickets, applicableDiscount);

        assertEquals(0.25, ticketLot.getApplicableDiscount());
    }

    @Test
    void testShouldReturnZeroDiscount() throws TicketLotConfigurationException {
        int id = 1;
        List<Ticket> tickets = new ArrayList<>();
        double applicableDiscount = -1;

        TicketLot ticketLot = this.ticketLotService.create(id, tickets, applicableDiscount);

        assertEquals(0, ticketLot.getApplicableDiscount());
    }

    @Test
    void shouldThrowExceptionCaseVipTicketsIsLowerThan20PercentOfTotalTicket() {
        // 19 Vip Tickets, 10 Meia entrada tickets, 71 normal tickets. Total of 100 tickets and vip tickets are 19%
        List<Ticket> allTickets = this.generateTickets(19, 71, 10);

        int id = 1;
        double applicableDiscount = 0.1;

        assertThrows(TicketLotConfigurationException.class, ()->{
            this.ticketLotService.create(id, allTickets, applicableDiscount);
        });

    }

    @Test
    void shouldThrowExceptionCaseVipTicketsIsMoreThan30PercentOfTotalTickets() {
        // 31 Vip Tickets, 10 Meia entrada tickets, 59 normal tickets. Total of 100 tickets and vip tickets are 31%
        List<Ticket> allTickets = this.generateTickets(31, 59, 10);

        int id = 1;
        double applicableDiscount = 0.1;

        assertThrows(TicketLotConfigurationException.class, ()->{
            this.ticketLotService.create(id, allTickets, applicableDiscount);
        });
    }

    @Test
    void shouldThrowExceptionCaseMeiaEntradaTicketsAreMoreThan10PercentOfTotalTickets() throws TicketLotConfigurationException {
        // 30 Vip Tickets, 11 Meia entrada tickets, 59 normal tickets. Total of 100 tickets and meia entrada tickets are 11%
        List<Ticket> allTickets = this.generateTickets(30, 59, 11);

        int id = 1;
        double applicableDiscount = 0.1;
        assertThrows(TicketLotConfigurationException.class, ()->{
            this.ticketLotService.create(id, allTickets, applicableDiscount);
        });
    }

    @Test
    void shouldThrowExceptionCaseMeiaEntradaTicketsAreLessThan10PercentOfTotalTickets() throws TicketLotConfigurationException {
        // 30 Vip Tickets, 9 Meia entrada tickets, 61 normal tickets. Total of 100 tickets and meia entrada tickets are 9%
        List<Ticket> allTickets = this.generateTickets(30, 61, 9);

        int id = 1;
        double applicableDiscount = 0.1;
        assertThrows(TicketLotConfigurationException.class, ()->{
            this.ticketLotService.create(id, allTickets, applicableDiscount);
        });
    }

    private List<Ticket> generateTickets(int viptickets, int normalTickets, int meiaEntradaTickets) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < viptickets; i++) {
            tickets.add(new Ticket(i, TicketType.VIP, 20.0));
        }
        for (int i = 0; i < normalTickets; i++) {
            tickets.add(new Ticket(i, TicketType.NORMAL, 10.0));
        }
        for (int i = 0; i < meiaEntradaTickets; i++) {
            tickets.add(new Ticket(i, TicketType.MEIA_ENTRADA, 5.0));
        }
        return tickets;
    }


    private List<Ticket> generateTickets(int quantity, TicketType type) {
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < quantity ; i++) {
            tickets.add(new Ticket(i, type, 20));
        }

        return tickets;
    }


}

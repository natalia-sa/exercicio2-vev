package ticket_system.functionalTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ticket_system.models.*;
import ticket_system.service.ConcertService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquivalencePartitionTest {

    private ConcertService concertService;


    @BeforeEach
    void setup() {
        this.concertService = new ConcertService();
    }

    @Test
    void shouldGenerateAProfitConcertReport() {
        Concert concert = new Concert(LocalDate.of(2020, 10, 12), "Japãozinho", 1000.0, 2000.0, false);
        List<Ticket> tickets = this.createTickets(100, 350, 50);
        TicketLot ticketLot = new TicketLot(1, tickets, 0.15);
        this.concertService.addTicketLotToConcert(concert, ticketLot);

        // Sell all tickets
        tickets.forEach(Ticket::sell);

        ConcertReport report = concertService.generateConcertReport(concert);

        assertEquals(100, report.getSoldVipTickets());
        assertEquals(350, report.getSoldNormalTickets());
        assertEquals(50, report.getSoldMeiaEntradatickets());

        assertEquals(1888.0, report.getNetRevenue());

        assertEquals(1888.0, report.getNetRevenue());

        assertEquals(ConcertReportStatus.PROFIT, report.getStatus());
    }

    @Test
    void shouldGenerateLossConcertReport() {
        Concert concert = new Concert(LocalDate.of(2020, 10, 12), "Japãozinho", 1000.0, 8000.0, true);
        List<Ticket> tickets = this.createTickets(100, 350, 50);
        TicketLot ticketLot = new TicketLot(1, tickets, 0.15);
        this.concertService.addTicketLotToConcert(concert, ticketLot);

        // Sell all tickets
        tickets.forEach(Ticket::sell);

        ConcertReport report = concertService.generateConcertReport(concert);

        assertEquals(100, report.getSoldVipTickets());
        assertEquals(350, report.getSoldNormalTickets());
        assertEquals(50, report.getSoldMeiaEntradatickets());
        assertEquals(-2112.0, report.getNetRevenue());
        assertEquals(ConcertReportStatus.LOSS, report.getStatus());
    }

    @Test
    void shouldGenerateStableConcertReport() {
        Concert concert = new Concert(LocalDate.of(2020, 10, 12), "Japãozinho", 2468.0, 3226, true);
        List<Ticket> tickets = this.createTickets(100, 350, 50);
        TicketLot ticketLot = new TicketLot(1, tickets, 0.15);
        this.concertService.addTicketLotToConcert(concert, ticketLot);

        // Sell all tickets
        tickets.forEach(Ticket::sell);

        ConcertReport report = concertService.generateConcertReport(concert);

        assertEquals(100, report.getSoldVipTickets());
        assertEquals(350, report.getSoldNormalTickets());
        assertEquals(50, report.getSoldMeiaEntradatickets());
        assertEquals(0.0, report.getNetRevenue());
        assertEquals(ConcertReportStatus.STABLE, report.getStatus());
    }

    private List<Ticket> createTickets(int viptickets, int normalTickets, int meiaEntradaTickets) {
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

}
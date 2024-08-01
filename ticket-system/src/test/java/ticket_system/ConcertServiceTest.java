package ticket_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ticket_system.models.Concert;
import ticket_system.models.Ticket;
import ticket_system.models.TicketLot;
import ticket_system.models.TicketType;
import ticket_system.service.ConcertService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcertServiceTest {

    private ConcertService concertService;
    private Concert specialDateConcert;


    @BeforeEach
    void setup() {
        this.concertService = new ConcertService();
        this.specialDateConcert = new Concert( LocalDate.of(2020, 12, 10), "Japãozinho", 2000, 2000, true);
    }

    @Test
    void shouldCreateConcert() {
        LocalDate date = LocalDate.of(2020, 12, 10);
        String artist = "Japãozinho";
        double cache = 2000;
        double infrastructureCosts = 2000;
        boolean isInSpecialDate = false;

        Concert expectedConcert = new Concert(date, artist, cache, infrastructureCosts, isInSpecialDate);

        Concert concert = this.concertService.create(date, artist, cache, infrastructureCosts, isInSpecialDate);

        assertEquals(concert, expectedConcert);
    }

    @Test
    void shouldAddTicketLotToConcert() {
        assertEquals(0, this.specialDateConcert.getTicketLots().size());

        TicketLot ticketLot = new TicketLot(1, new ArrayList<>(), 0.1);
        this.concertService.addTicketLotToConcert(this.specialDateConcert, ticketLot);

        assertEquals(1, this.specialDateConcert.getTicketLots().size());

    }

    @Test
    void shouldGenerateAProfitConcertReport() {
        Concert concert = new Concert(LocalDate.of(2020, 10, 12), "Japãozinho", 1000.0, 2000.0, true);
        List<Ticket> tickets = this.createTickets(100, 350, 50);
        TicketLot ticketLot = new TicketLot(1, tickets, 0.15);
        this.concertService.addTicketLotToConcert(concert, ticketLot);

        // Sell all tickets
        tickets.forEach(Ticket::sell);

        ConcertReport report = concertService.generateConcertReport(concert);

        assertEquals(100, report.getSoldVipVendidos());
        assertEquals(350, report.getSoldNormaisVendidos());
        assertEquals(50, report.getSoldMeiaEntradaVendidos());
        assertEquals(1625.0, report.getNetRevenue());
        assertEquals(ConcertReportStatus.PROFIT, report.getStatusFinanceiro());
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

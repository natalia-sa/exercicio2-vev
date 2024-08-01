package ticket_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ticket_system.models.Concert;
import ticket_system.models.TicketLot;
import ticket_system.service.ConcertService;

import java.time.LocalDate;
import java.util.ArrayList;

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



}

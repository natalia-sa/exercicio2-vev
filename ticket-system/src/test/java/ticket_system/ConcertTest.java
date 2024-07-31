package ticket_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ticket_system.models.Ticket;
import ticket_system.models.TicketType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcertTest {

    private ConcertService concertService;

    @BeforeEach
    void setup() {
        this.concertService = new ConcertService();
    }

    @Test
    void shouldCreateConcert() {
        LocalDate date = LocalDate.now();
        String artist = "Japãozinho";
        double cache = 2000;
        double infrastructureCosts = 2000;
        boolean isInSpecialDate = false;

        Concert expectedConcert = new Concert(date, artist, cache, infrastructureCosts, isInSpecialDate);

        Concert concert = this.concertService.create();

        assertEquals(concert, expectedConcert);
    }



}

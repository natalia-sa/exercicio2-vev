package ticket_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ticket_system.models.Concert;
import ticket_system.service.ConcertService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcertServiceTest {

    private ConcertService concertService;

    @BeforeEach
    void setup() {
        this.concertService = new ConcertService();
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



}

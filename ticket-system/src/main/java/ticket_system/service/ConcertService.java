package ticket_system.service;

import ticket_system.models.Concert;

import java.time.LocalDate;

public class ConcertService {

    public Concert create(LocalDate date, String artist, double cache, double infrastructureCosts, boolean isInSpecialDate) {
        return new Concert(date, artist, cache, infrastructureCosts, isInSpecialDate);
    }
}

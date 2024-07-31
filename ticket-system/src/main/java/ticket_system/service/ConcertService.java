package ticket_system.service;

import ticket_system.models.Concert;

import java.time.LocalDate;

public class ConcertService {

    public Concert create() {
        return new Concert(LocalDate.of(2020, 12, 10), "Japãozinho", 2000, 2000, false);
    }
}

package ticket_system.service;

import ticket_system.models.Ticket;
import ticket_system.models.TicketLot;

import java.util.ArrayList;

public class TicketLotService {

    public TicketLot create() {
        return new TicketLot(1, new ArrayList<Ticket>(), 0.5);
    }
}

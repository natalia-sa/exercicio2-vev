package ticket_system.service;

import ticket_system.models.Ticket;
import ticket_system.models.TicketLot;

import java.util.List;

public class TicketLotService {

    public TicketLot create(int id, List<Ticket> tickets, double applicableDiscount) {
        return new TicketLot(id, tickets, applicableDiscount);
    }
}

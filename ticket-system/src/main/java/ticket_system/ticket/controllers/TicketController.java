package ticket_system.ticket.controllers;

import ticket_system.models.Ticket;
import ticket_system.models.TicketStatus;
import ticket_system.models.TicketType;

public class TicketController {

    public Ticket create() {
        return new Ticket(1, TicketType.MEIA_ENTRADA, TicketStatus.NOT_SOLD, 20.0);
    }
}
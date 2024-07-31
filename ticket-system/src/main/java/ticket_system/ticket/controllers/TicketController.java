package ticket_system.ticket.controllers;

import ticket_system.models.Ticket;
import ticket_system.models.TicketStatus;
import ticket_system.models.TicketType;

public class TicketController {

    public Ticket create(int id, TicketType ticketType, double price) {
        return new Ticket(id, ticketType, price);
    }
}
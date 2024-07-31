package ticket_system.ticket.service;

import ticket_system.exceptions.TicketAlreadySoldException;
import ticket_system.models.Ticket;
import ticket_system.models.TicketStatus;
import ticket_system.models.TicketType;

public class TicketService {

    public Ticket create(int id, TicketType ticketType, double price) {
        return new Ticket(id, ticketType, price);
    }
}
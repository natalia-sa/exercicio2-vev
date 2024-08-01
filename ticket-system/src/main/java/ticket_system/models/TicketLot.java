package ticket_system.models;

import java.util.List;
import java.util.Objects;

public class TicketLot {
    private int id;
    private List<Ticket> tickets;
    private double applicableDiscount;

    public TicketLot(int id, List<Ticket> tickets, double applicableDiscount) {
        this.id = id;
        this.tickets = tickets;
        this.applicableDiscount = Math.min(0.25, applicableDiscount);
    }

    public int getId() {
        return id;
    }

    public double getApplicableDiscount() {
        return applicableDiscount;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketLot)) return false;
        TicketLot ticketLot = (TicketLot) o;
        return id == ticketLot.id && Double.compare(applicableDiscount, ticketLot.applicableDiscount) == 0 && Objects.equals(tickets, ticketLot.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tickets, applicableDiscount);
    }
}

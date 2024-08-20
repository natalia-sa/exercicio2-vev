package ticket_system.models;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TicketLot {
    private final int id;
    private final List<Ticket> tickets;
    private final double applicableDiscount;

    public TicketLot(int id, List<Ticket> tickets, double applicableDiscount) {
        this.id = id;
        this.tickets = tickets;
        this.applicableDiscount = Math.min(0.25, Math.max(applicableDiscount, 0));
    }


    public double getGrossIncome() {
        double grossIncome = 0.0;
        for (Ticket ticket : this.tickets) {
            if (ticket.isSold()) {
                double ticketValue = ticket.getPrice(this.applicableDiscount);
                grossIncome += ticketValue;
            }
        }

        return grossIncome;
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

    public List<Ticket> getSoldVipTickets() {
        return this.getTicketsByTypeAndStatus(TicketType.VIP, TicketStatus.SOLD);
    }

    public List<Ticket> getSoldNormalTickets() {
        return this.getTicketsByTypeAndStatus(TicketType.NORMAL, TicketStatus.SOLD);
    }

    public List<Ticket> getSoldMeiaEntradaTickets() {
        return  this.getTicketsByTypeAndStatus(TicketType.MEIA_ENTRADA, TicketStatus.SOLD);
    }

    private List<Ticket> getTicketsByTypeAndStatus(TicketType type, TicketStatus ticketStatus) {
        return this.tickets.stream()
                .filter(ticket -> ticket.getTicketType().equals(type) && ticket.getTicketStatus().equals(ticketStatus))
                .collect(Collectors.toList());
    }
}

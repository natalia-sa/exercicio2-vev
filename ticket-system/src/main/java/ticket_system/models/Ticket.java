package ticket_system.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

public class Ticket {

    private final int id;
    private final TicketType ticketType;
    private TicketStatus ticketStatus;
    private final double price;

    public Ticket(int id, TicketType ticketType, TicketStatus ticketStatus, double price) {
        this.id = id;
        this.ticketType = ticketType;
        this.ticketStatus = ticketStatus;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void sell() {
        this.ticketStatus = TicketStatus.SOLD;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && Double.compare(price, ticket.price) == 0 && ticketType == ticket.ticketType && ticketStatus == ticket.ticketStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticketType, ticketStatus, price);
    }
}

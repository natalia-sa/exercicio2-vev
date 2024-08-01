package ticket_system.service;

import ticket_system.exceptions.TicketLotConfigurationException;
import ticket_system.models.Ticket;
import ticket_system.models.TicketLot;
import ticket_system.models.TicketType;

import java.util.List;
import java.util.stream.Collectors;

public class TicketLotService {

    public TicketLot create(int id, List<Ticket> tickets, double applicableDiscount) throws TicketLotConfigurationException {
        this.validateTicketsDistribution(tickets);
        return new TicketLot(id, tickets, applicableDiscount);
    }

    private void validateTicketsDistribution(List<Ticket> tickets) throws TicketLotConfigurationException {
        this.validateVipTicketDistribution(tickets);
    }

    private void validateVipTicketDistribution(List<Ticket> tickets) throws TicketLotConfigurationException {
        this.validateIfVipTicketsAreLessThan20PercentOfTotalTickets(tickets);
    }

    private void validateIfVipTicketsAreLessThan20PercentOfTotalTickets(List<Ticket> tickets) throws TicketLotConfigurationException {
        int totalVipTickets = this.getTicketsByType(tickets, TicketType.VIP).size();
        int totalTickets = tickets.size();

        double TWENTY_PERCENT = 0.20;
        double twentyPercentOfTotal = totalTickets * TWENTY_PERCENT;

        if (totalVipTickets < twentyPercentOfTotal) {
            throw new TicketLotConfigurationException("Vip tickets are less than 20% of total tickets. Vip Tickets should be beetwen 20% and 30% of total tickets.");
        }

    }


    private List<Ticket> getTicketsByType(List<Ticket> tickets, TicketType type) {
        return tickets.stream()
                .filter(ticket -> ticket.getTicketType() == type)
                .collect(Collectors.toList());
    }

}

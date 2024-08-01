package ticket_system.service;

import ticket_system.exceptions.TicketLotConfigurationException;
import ticket_system.models.Ticket;
import ticket_system.models.TicketLot;
import ticket_system.models.TicketType;

import java.util.List;
import java.util.stream.Collectors;

public class TicketLotService {

    private final double TEN_PERCENT = 0.10;

    public TicketLot create(int id, List<Ticket> tickets, double applicableDiscount) throws TicketLotConfigurationException {
        this.validateTicketsDistribution(tickets);
        return new TicketLot(id, tickets, applicableDiscount);
    }

    private void validateTicketsDistribution(List<Ticket> tickets) throws TicketLotConfigurationException {
        this.validateVipTicketDistribution(tickets);
        this.validateMeiaEntradaTicketDistribution(tickets);
    }

    private void validateMeiaEntradaTicketDistribution(List<Ticket> tickets) throws TicketLotConfigurationException {
        this.validateIfMeiaEntradaTicketsAreMoreThan10PercentOfTotalTickets(tickets);
        this.validateIfMeiaEntradaTicketsAreLessThan10PercentOfTotalTickets(tickets);
    }

    private void validateVipTicketDistribution(List<Ticket> tickets) throws TicketLotConfigurationException {
        this.validateIfVipTicketsAreLessThan20PercentOfTotalTickets(tickets);
        this.validateIfVipTicketsAreMoreThan30PercentOfTotalTickets(tickets);
    }

    private void validateIfMeiaEntradaTicketsAreMoreThan10PercentOfTotalTickets(List<Ticket> tickets) throws TicketLotConfigurationException {
        int totalMeiaEntradaTickets = this.getTicketsByType(tickets, TicketType.MEIA_ENTRADA).size();
        int totalTickets = tickets.size();

        double tenPercentOfTotal = totalTickets * this.TEN_PERCENT;

        if (totalMeiaEntradaTickets > tenPercentOfTotal) {
            throw new TicketLotConfigurationException("Meia Entrada tickets are more than 10% of total tickets. Meia Entrada Tickets should be 10% of total tickets.");
        }

    }

    private void validateIfMeiaEntradaTicketsAreLessThan10PercentOfTotalTickets(List<Ticket> tickets) throws TicketLotConfigurationException {
        int totalMeiaEntradaTickets = this.getTicketsByType(tickets, TicketType.MEIA_ENTRADA).size();
        int totalTickets = tickets.size();

        double tenPercentOfTotal = totalTickets * this.TEN_PERCENT;

        if (totalMeiaEntradaTickets < tenPercentOfTotal) {
            throw new TicketLotConfigurationException("Meia Entrada tickets are less than 10% of total tickets. Meia Entrada Tickets should be 10% of total tickets.");
        }

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

    private void validateIfVipTicketsAreMoreThan30PercentOfTotalTickets(List<Ticket> tickets) throws TicketLotConfigurationException {
        int totalVipTickets = this.getTicketsByType(tickets, TicketType.VIP).size();
        int totalTickets = tickets.size();

        double THIRTY_PERCENT = 0.30;
        double thirtyPercentOfTotal = totalTickets * THIRTY_PERCENT;

        if (totalVipTickets > thirtyPercentOfTotal) {
            throw new TicketLotConfigurationException("Vip tickets are more than 30% of total tickets. Vip Tickets should be beetwen 20% and 30% of total tickets.");
        }

    }


    private List<Ticket> getTicketsByType(List<Ticket> tickets, TicketType type) {
        return tickets.stream()
                .filter(ticket -> ticket.getTicketType().equals(type))
                .collect(Collectors.toList());
    }

}

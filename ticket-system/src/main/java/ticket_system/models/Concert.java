package ticket_system.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Concert {
    private final LocalDate date;
    private final String artist;
    private final double cache;
    private final double infrastructureCosts;
    private final List<TicketLot> ticketLots;
    private final boolean isInSpecialDate;

    public Concert(LocalDate date, String artist, double cache, double infrastructureCosts, boolean isInSpecialDate) {
        this.date = date;
        this.artist = artist;
        this.cache = cache;
        this.infrastructureCosts = infrastructureCosts;
        this.isInSpecialDate = isInSpecialDate;
        this.ticketLots = new ArrayList<>();
    }

    public ConcertReport generateConcertReport() {
        double netRevenue = this.getNetRevenue();
        ConcertReportStatus concertReportStatus = this.getConcertReportStatus();
        int soldVipTickets = this.getTotalOfSoldVipTickets();
        int soldNormalTickets = this.getTotalOfSoldNormalTickets();
        int soldMeiaEntradatickets = this.getTotalOfSoldMeiaEntradaTickets();

        return new ConcertReport(soldVipTickets, soldNormalTickets, soldMeiaEntradatickets, netRevenue, concertReportStatus);
    }

    private double getTotalCosts() {
        double totalCosts = this.cache + this.infrastructureCosts;

        double DISCOUNT_FOR_SPECIAL_DAYS = 1.15;

        if (isInSpecialDate) {
            totalCosts *= DISCOUNT_FOR_SPECIAL_DAYS;
        }

        return Math.round(totalCosts);
    }

    private double getGrossIncome() {
        double grossIncome = 0.0;
        for (TicketLot ticketLot : this.ticketLots) {
            grossIncome += ticketLot.getGrossIncome();
        }

        return Math.round(grossIncome);
    }

    private int getTotalOfSoldVipTickets() {
        int soldVipTickets = 0;

        for (TicketLot ticketLot : this.ticketLots) {
            soldVipTickets += ticketLot.getSoldVipTickets().size();
        }

        return soldVipTickets;
    }

    private int getTotalOfSoldNormalTickets() {
        int soldNormalTickets = 0;

        for (TicketLot ticketLot : this.ticketLots) {
            soldNormalTickets += ticketLot.getSoldNormalTickets().size();
        }

        return soldNormalTickets;    }

    private int getTotalOfSoldMeiaEntradaTickets() {
        int soldMeiaEntradaTickets = 0;

        for (TicketLot ticketLot : this.ticketLots) {
            soldMeiaEntradaTickets += ticketLot.getSoldMeiaEntradaTickets().size();
        }

        return soldMeiaEntradaTickets;
    }

    private double getNetRevenue() {
        return Math.round(this.getGrossIncome() - this.getTotalCosts());
    }

    private ConcertReportStatus getConcertReportStatus() {
        double netRevenue = this.getNetRevenue();
        return netRevenue > 0 ? ConcertReportStatus.PROFIT : netRevenue == 0 ? ConcertReportStatus.STABLE : ConcertReportStatus.LOSS;

    }

    public void addTicketLot(TicketLot ticketLot) {
        this.ticketLots.add(ticketLot);
    }

    public double getCache() {
        return cache;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getArtist() {
        return artist;
    }

    public List<TicketLot> getTicketLots() {
        return ticketLots;
    }

    public double getInfrastructureCosts() {
        return infrastructureCosts;
    }

    public boolean isInSpecialDate() {
        return isInSpecialDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Concert)) return false;
        Concert concert = (Concert) o;
        return Double.compare(cache, concert.cache) == 0 && Double.compare(infrastructureCosts, concert.infrastructureCosts) == 0 && isInSpecialDate == concert.isInSpecialDate && Objects.equals(date, concert.date) && Objects.equals(artist, concert.artist) && Objects.equals(ticketLots, concert.ticketLots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, artist, cache, infrastructureCosts, ticketLots, isInSpecialDate);
    }
}
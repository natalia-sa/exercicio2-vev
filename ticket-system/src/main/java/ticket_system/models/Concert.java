package ticket_system.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Concert {
    private LocalDate date;
    private String artist;
    private double cache;
    private double infrastructureCosts;
    private List<TicketLot> ticketLots;
    private boolean isInSpecialDate;

    public Concert(LocalDate date, String artist, double cache, double infrastructureCosts, boolean isInSpecialDate) {
        this.date = date;
        this.artist = artist;
        this.cache = cache;
        this.infrastructureCosts = infrastructureCosts;
        this.isInSpecialDate = isInSpecialDate;
        this.ticketLots = new ArrayList<>();
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
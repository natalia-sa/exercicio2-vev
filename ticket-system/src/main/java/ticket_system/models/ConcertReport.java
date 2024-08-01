package ticket_system.models;

import java.util.Objects;

public class ConcertReport {
    private final int soldVipTickets;
    private final int soldNormalTickets;
    private final int soldMeiaEntradatickets;
    private final double netRevenue;
    private final ConcertReportStatus status;

    public ConcertReport(int soldVipTickets, int soldNormalTickets, int soldMeiaEntradatickets, double netRevenue, ConcertReportStatus status) {
        this.soldNormalTickets = soldNormalTickets;
        this.soldVipTickets = soldVipTickets;
        this.soldMeiaEntradatickets = soldMeiaEntradatickets;
        this.netRevenue = netRevenue;
        this.status = status;
    }

    public int getSoldNormalTickets() {
        return soldNormalTickets;
    }

    public int getSoldVipTickets() {
        return soldVipTickets;
    }

    public int getSoldMeiaEntradatickets() {
        return soldMeiaEntradatickets;
    }

    public double getNetRevenue() {
        return netRevenue;
    }

    public ConcertReportStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConcertReport)) return false;
        ConcertReport that = (ConcertReport) o;
        return soldVipTickets == that.soldVipTickets && soldNormalTickets == that.soldNormalTickets && soldMeiaEntradatickets == that.soldMeiaEntradatickets && Double.compare(netRevenue, that.netRevenue) == 0 && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(soldVipTickets, soldNormalTickets, soldMeiaEntradatickets, netRevenue, status);
    }
}

package ticket_system.functionalTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ticket_system.exceptions.TicketLotConfigurationException;
import ticket_system.models.Ticket;
import ticket_system.models.TicketLot;
import ticket_system.service.TicketLotService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LimitValueAnalysisTest {

    private TicketLotService ticketLotService;

    @BeforeEach
    void setup() {
        this.ticketLotService = new TicketLotService();
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 0.25, 0.1, 0.24, 0.17})
    void shouldCreateLotWithAcceptableDiscounts(double discount) throws TicketLotConfigurationException {
        int id = 1;
        List<Ticket> tickets = new ArrayList<>();

        TicketLot ticketLot = this.ticketLotService.create(id, tickets, discount);

        assertEquals(discount, ticketLot.getApplicableDiscount());
    }

    @Test
    void testShouldReturnMaxDiscount() throws TicketLotConfigurationException {
        int id = 1;
        List<Ticket> tickets = new ArrayList<>();
        double applicableDiscount = 0.5;

        TicketLot ticketLot = this.ticketLotService.create(id, tickets, applicableDiscount);

        assertEquals(0.25, ticketLot.getApplicableDiscount());
    }


    @Test
    void testShouldReturnZeroDiscount() throws TicketLotConfigurationException {
        int id = 1;
        List<Ticket> tickets = new ArrayList<>();
        double applicableDiscount = -1;

        TicketLot ticketLot = this.ticketLotService.create(id, tickets, applicableDiscount);

        assertEquals(0, ticketLot.getApplicableDiscount());
    }
}

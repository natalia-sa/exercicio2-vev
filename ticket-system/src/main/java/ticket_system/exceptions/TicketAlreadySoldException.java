package ticket_system.exceptions;

public class TicketAlreadySoldException extends Exception {
     public TicketAlreadySoldException(String message) {
         super(message);
     }
}

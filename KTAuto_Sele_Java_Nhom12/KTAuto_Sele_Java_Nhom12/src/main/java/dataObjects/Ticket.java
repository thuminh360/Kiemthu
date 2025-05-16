package dataObjects;

import dataObjects.enumData.*;

public class Ticket {
    private String id;
    private String departDate;
    private DepartStation departFrom;
    private ArriveStation arriveAt;
    private SeatType seatType;
    private TicketAmount ticketAmount;


    public Ticket(String id,String departDate, DepartStation departFrom, ArriveStation arriveAt,
                  SeatType seatType, TicketAmount ticketAmount) {
        this.id= id;
        this.departDate = departDate;
        this.departFrom = departFrom;
        this.arriveAt = arriveAt;
        this.seatType = seatType;
        this.ticketAmount = ticketAmount;
    }
    public String getId() {
        return id;
    }
    public String getDepartDate() {
        return departDate;
    }

    public DepartStation getDepartFrom() {
        return departFrom;
    }

    public ArriveStation getArriveAt() {
        return arriveAt;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public TicketAmount getTicketAmount() {
        return ticketAmount;
    }
}

package com.census.support.scheduler;

import com.census.support.ticket.Ticket;
import com.census.support.ticket.TicketRepository;
import com.census.support.util.SysMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Configuration
@EnableScheduling
public class HoldTimeOutUpdateSch {
    @Autowired
    private TicketRepository ticketRepository;

    @Scheduled(fixedDelay =6000, initialDelay = 10)
    public void insertDailyAttendanceDataFromRowData()
    {
        List<Ticket> tickets = this.ticketRepository.findByStatus(SysMessage.HOLD_STS);
        List<Ticket> ticketsSaveList = new LinkedList<>();
        tickets.forEach(ticket -> {
            // subtract between wo time
            long diff = ((new Date().getTime() - ticket.getHoldTime().getTime())/1000)/60;
            diff=Math.abs(diff);
            long timeLeftInMin=Long.parseLong(ticket.getHoldDuration())-diff;

            if(timeLeftInMin<=0){
                ticket.setStatusSequence(1L);
                ticketsSaveList.add(ticket);
            }
        });

        this.ticketRepository.saveAll(ticketsSaveList);
    }
}

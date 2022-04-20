package com.census.support.ticket.entityListener;

import com.census.support.ticket.Ticket;
import com.census.support.ticket.TicketLog;
import com.census.support.util.BeanUtil;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;
import java.util.logging.Logger;

import static javax.transaction.Transactional.TxType.MANDATORY;

public class TicketListener {
    Logger log = Logger.getLogger(TicketListener.class.getName());

    @PrePersist
    public void prePersist(Ticket entity) {
        // Persistence logic
        log.info("TicketLog ...INSERT");
        perform(entity, "INSERT");
    }

    @PreUpdate
    public void preUpdate(Ticket entity) {
        // update logic
        log.info("TicketLog ...UPDATE");
        perform(entity, "UPDATE");
    }

    @PreRemove
    public void preRemove(Ticket entity) {
        // Removal logic
        log.info("TicketLog ...DELETE");
        perform(entity, "DELETE");
    }

    @Transactional(MANDATORY)
    void perform(Ticket entity, String action) {
        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        entityManager.persist(new TicketLog(entity, action));

    }
}

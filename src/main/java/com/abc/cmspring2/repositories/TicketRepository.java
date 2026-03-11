package com.abc.cmspring2.repositories;

import com.abc.cmspring2.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}

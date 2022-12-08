package com.example.repository;

import com.example.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ITicketRepository extends JpaRepository<Ticket, Integer> {

    @Query(value = "select * \n" +
            "from ticket t \n" +
            "where t.id = :id " +
            "and t.status = false",
            nativeQuery = true)
    Optional<Ticket> findTicketById(@Param("id") int id);

    @Query(value = "select * " +
            "from ticket " +
            "where ticket.status = false " +
            "and ticket.start like %:start% " +
            "and ticket.end like %:end% " +
            "and DATE(ticket.start_day) BETWEEN date(:startDay) AND date(:endDay)",
            nativeQuery = true)
    Page<Ticket> findAndSearch(@Param("start") String start,
                               @Param("end") String end,
                               @Param("startDay") String startDay,
                               @Param("endDay") String endDay, Pageable pageable);
}

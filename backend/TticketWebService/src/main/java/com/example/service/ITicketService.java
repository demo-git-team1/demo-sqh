package com.example.service;

import com.example.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITicketService {

    Optional<Ticket> findById(Integer id);

    void save(Ticket ticket);

    Optional<Ticket> findTicketById(int id);

    Page<Ticket> findAll(Pageable pageable);

    Page<Ticket> findAll(String start, String end, String startDay, String endDay, Pageable pageable);
}

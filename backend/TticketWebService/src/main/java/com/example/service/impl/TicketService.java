package com.example.service.impl;

import com.example.model.Ticket;
import com.example.repository.ITicketRepository;
import com.example.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService implements ITicketService {

    @Autowired
    private ITicketRepository ticketRepository;

    @Override
    public Optional<Ticket> findById(Integer id) {
        return ticketRepository.findById(id);
    }

    @Override
    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> findTicketById(int id) {
        return ticketRepository.findTicketById(id);
    }

    @Override
    public Page<Ticket> findAll(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    @Override
    public Page<Ticket> findAll(String start, String end, String startDay, String endDay, Pageable pageable) {
        return ticketRepository.findAndSearch( start, end, startDay, endDay, pageable);
    }
}

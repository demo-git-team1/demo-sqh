package com.example.controller;

import com.example.dto.TicketDTO;
import com.example.model.ErrorTicket;
import com.example.model.Ticket;
import com.example.service.ITicketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/tickets")
public class TicketRestController {
    @Autowired
    private ITicketService ticketService;
    @GetMapping("")
    public ResponseEntity<List<TicketDTO>> findAllTicket(@PageableDefault(value = 3) Pageable pageable) {
        Page<Ticket> ticketPage = ticketService.findAll(pageable);
        List<Ticket> ticketList = ticketPage.getContent();
        List<TicketDTO> ticketDTOList = new ArrayList<>();
        ticketList.forEach(ticket -> {
            TicketDTO ticketDTO = new TicketDTO();
            BeanUtils.copyProperties(ticket, ticketDTO);
            ticketDTOList.add(ticketDTO);
        });
        return new ResponseEntity<>(ticketDTOList, HttpStatus.OK);
    }


    /**
     * Lấy ra page<Ticket> và search
     * @param start: Điểm đi Thông tin search
     * @param end: Điểm đến Thông tin search
     * @param startDay: Ngày bắt đầu thông tin seach
     * @param endDay: Ngày kết thúc thông tin seach
     * @param pageable
     * @return
     */
    @GetMapping("/pg")
    public ResponseEntity<?> findAllTicketPg(@RequestParam(required = false, defaultValue = "") String start,
                                                        @RequestParam(required = false, defaultValue = "") String end,
                                                        @RequestParam(required = false, defaultValue = "") String startDay,
                                                        @RequestParam(required = false, defaultValue = "") String endDay,
                                                        @PageableDefault(value = 3) Pageable pageable) {
        Page<Ticket> ticketPageAndSearch = this.ticketService.findAll(start, end, startDay, endDay, pageable);
        return new ResponseEntity<>(ticketPageAndSearch, HttpStatus.OK);
    }


    /**
     * Lấy ticketDTO theo id
     * @param id
     * @return ResponseEntity<TicketDTO>
     */
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> findById(@PathVariable() int id) {
        Optional<Ticket> ticketOptional = ticketService.findById(id);
        if(!ticketOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TicketDTO ticketDTO = new TicketDTO();
        BeanUtils.copyProperties(ticketOptional.get(), ticketDTO);
        return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
    }

    /**
     * Cập nhật thông tin cho ticket, có validate
     * @param id id ticket sẽ cập nhật
     * @param ticketDTO thông tin TicketDto sau cập nhật
     * @param bindingResult
     * @return ResponseEntity<TicketDTO>
     */
    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable int id,
                                                  @Validated @RequestBody TicketDTO ticketDTO,
                                                  BindingResult bindingResult) {

        new TicketDTO().validate(ticketDTO, bindingResult);
        if(bindingResult.hasFieldErrors("price") || bindingResult.hasFieldErrors("quality")) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        Optional<Ticket> ticketOptional = ticketService.findTicketById(id);
        if(ticketOptional.isPresent()) {
            Ticket ticket = new Ticket();
            BeanUtils.copyProperties(ticketDTO, ticket);
            ticketService.save(ticket);
            return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Thêm mới ticket có validate trả về vé được tạo mới
     * @param ticketDTO vé được tạo mới
     * @param bindingResult
     * @return ResponseEntity<TicketDTO>
     */
    @PostMapping("")
    public ResponseEntity<?> createTicket(@Validated @RequestBody TicketDTO ticketDTO,
                                               BindingResult bindingResult) {
        new TicketDTO().validate(ticketDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            List<ErrorTicket> errorTickets = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorTickets.add(new ErrorTicket(fieldError.getField(), fieldError.getDefaultMessage()));
            });
            return new ResponseEntity<>(errorTickets, HttpStatus.METHOD_NOT_ALLOWED);
        }
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(ticketDTO, ticket);
        ticketService.save(ticket);
        return new ResponseEntity<>(ticketDTO, HttpStatus.CREATED);
    }

    /**
     * Xóa mềm ticket theo id
     * @param id id ticket bị xóa
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable int id) {
        Optional<Ticket> ticketOptional = ticketService.findById(id);
        if(ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setStatus(true);
            ticketService.save(ticket);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

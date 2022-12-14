package com.example.dto;

import com.example.model.Garage;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TicketDTO implements Validator {
    private Integer id;
    private Double price;
    private String start;
    private String end;
    private String startDay;
    private String startHours;
    private Integer quality;
    private Garage garage;
    private boolean status;

    public TicketDTO() {
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getStartHours() {
        return startHours;
    }

    public void setStartHours(String startHours) {
        this.startHours = startHours;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        TicketDTO ticketDTO = (TicketDTO) target;
        String start = ticketDTO.start;
        String end = ticketDTO.end;
        Double price = ticketDTO.getPrice();
        String startDay = ticketDTO.getStartDay();
        String startHours = ticketDTO.getStartHours();
        Integer quality = ticketDTO.getQuality();
        String regexDay = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
        String regexHours = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";

        //validate start
        if(start == null) {
            errors.rejectValue("start", "", "Kh??ng ???????c b??? tr???ng");
        } else if("".equals(start.trim())) {
            errors.rejectValue("start", "", "Kh??ng ???????c b??? tr???ng");
        } else if(start.length() < 3 || start.length() > 30) {
            errors.rejectValue("start", "", "S??? k?? t??? t???i thi???u: 3, t???i ??a 30");
        }

        //validate end
        if(end == null) {
            errors.rejectValue("end", "", "Kh??ng ???????c b??? tr???ng");
        } else if("".equals(end.trim())) {
            errors.rejectValue("end", "", "Kh??ng ???????c b??? tr???ng");
        } else if(end.length() < 3 || end.length() > 30) {
            errors.rejectValue("end", "", "S??? k?? t??? t???i thi???u: 3, t???i ??a 30");
        }

        //validate price
        if(price == null) {
            errors.rejectValue("price", "", "Kh??ng ???????c b??? tr???ng");
        } else if(price < 0 || price > 1000000) {
            errors.rejectValue("price", "", "Gi?? tr??? t???i thi???u: 0, t???i ??a: 1000000");
        }

        //validate startDay
        if(startDay == null || "".equals(startDay)) {
            errors.rejectValue("startDay", "", "Kh??ng ???????c b??? tr???ng");
        } else if (!startDay.matches(regexDay)) {
            errors.rejectValue("startDay", "", "Nh???p ????ng ?????nh d???ng sau: yyyy-mm-dd");
        } else {
            LocalDate now = LocalDate.now();
            LocalDate startLocalDate = LocalDate.parse(startDay);

            if(startLocalDate.compareTo(now) < 0) {
                errors.rejectValue("startDay", "", "B???n kh??ng th??? kh???i h??nh trong kh??a kh??? ???????c");
            }
        }

        //validate startHours
        if(startHours == null) {
            errors.rejectValue("startHours", "", "Kh??ng ???????c b??? tr???ng");
        } else if(startHours.trim().equals("")) {
            errors.rejectValue("startHours", "", "Kh??ng ???????c b??? tr???ng");
        } else if(!startHours.matches(regexHours)) {
            errors.rejectValue("startHours", "", "Vui l??ng nh???p ????ng ?????nh d???ng sau: hh:mm");
        } else {
            LocalDateTime now = LocalDateTime.now();
            String[] startHoursArr = startHours.split(":");
            if(now.getHour() > Integer.parseInt(startHoursArr[0])) {
                errors.rejectValue("startHours", "", "B???n ??ang ch???n gi??? kh???i h??nh trong kh??a kh???");
            } else if(now.getMinute() > Integer.parseInt(startHoursArr[1])) {
                errors.rejectValue("startHours", "", "B???n ??ang ch???n gi??? kh???i h??nh trong kh??a kh???");
            }
        }

        // validate quality
        if(quality == null) {
            errors.rejectValue("quality", "", "Kh??ng ???????c b??? tr???ng");
        } else if (quality < 0 || quality > 1000){
            errors.rejectValue("quality", "", "S??? l?????ng t???i thi???u: 0, t???i ??a: 1000");
        }

    }
}

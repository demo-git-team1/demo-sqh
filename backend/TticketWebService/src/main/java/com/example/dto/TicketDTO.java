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
            errors.rejectValue("start", "", "Không được bỏ trống");
        } else if("".equals(start.trim())) {
            errors.rejectValue("start", "", "Không được bỏ trống");
        } else if(start.length() < 3 || start.length() > 30) {
            errors.rejectValue("start", "", "Số ký tự tối thiểu: 3, tối đa 30");
        }

        //validate end
        if(end == null) {
            errors.rejectValue("end", "", "Không được bỏ trống");
        } else if("".equals(end.trim())) {
            errors.rejectValue("end", "", "Không được bỏ trống");
        } else if(end.length() < 3 || end.length() > 30) {
            errors.rejectValue("end", "", "Số ký tự tối thiểu: 3, tối đa 30");
        }

        //validate price
        if(price == null) {
            errors.rejectValue("price", "", "Không được bỏ trống");
        } else if(price < 0 || price > 1000000) {
            errors.rejectValue("price", "", "Giá trị tối thiểu: 0, tối đa: 1000000");
        }

        //validate startDay
        if(startDay == null || "".equals(startDay)) {
            errors.rejectValue("startDay", "", "Không được bỏ trống");
        } else if (!startDay.matches(regexDay)) {
            errors.rejectValue("startDay", "", "Nhập đúng định dạng sau: yyyy-mm-dd");
        } else {
            LocalDate now = LocalDate.now();
            LocalDate startLocalDate = LocalDate.parse(startDay);

            if(startLocalDate.compareTo(now) < 0) {
                errors.rejectValue("startDay", "", "Bạn không thể khởi hành trong khóa khứ được");
            }
        }

        //validate startHours
        if(startHours == null) {
            errors.rejectValue("startHours", "", "Không được bỏ trống");
        } else if(startHours.trim().equals("")) {
            errors.rejectValue("startHours", "", "Không được bỏ trống");
        } else if(!startHours.matches(regexHours)) {
            errors.rejectValue("startHours", "", "Vui lòng nhập đúng định dạng sau: hh:mm");
        } else {
            LocalDateTime now = LocalDateTime.now();
            String[] startHoursArr = startHours.split(":");
            if(now.getHour() > Integer.parseInt(startHoursArr[0])) {
                errors.rejectValue("startHours", "", "Bạn đang chọn giờ khởi hành trong khóa khứ");
            } else if(now.getMinute() > Integer.parseInt(startHoursArr[1])) {
                errors.rejectValue("startHours", "", "Bạn đang chọn giờ khởi hành trong khóa khứ");
            }
        }

        // validate quality
        if(quality == null) {
            errors.rejectValue("quality", "", "Không được bỏ trống");
        } else if (quality < 0 || quality > 1000){
            errors.rejectValue("quality", "", "Số lượng tối thiểu: 0, tối đa: 1000");
        }

    }
}

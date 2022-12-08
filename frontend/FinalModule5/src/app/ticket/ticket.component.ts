import { Component, OnInit } from '@angular/core';
import {Ticket} from '../model/ticket';
import {TickService} from '../service/tick.service';
import {Gara} from '../model/gara';
import {GaraService} from '../service/gara.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {PageTicket} from '../model/page-ticket';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css']
})
export class TicketComponent implements OnInit {
  pageTickets: PageTicket;
  garages: Gara[];
  rfSearch:FormGroup;
  startDay = '';// Mốc Ngày bắt đầu cho ngày khởi hành
  endDay = '';// Mốc Ngày kết thúc cho ngày khởi hành
  garageDefault: Gara = {
    id: 1,
    name: "garage1",
    status: false
  }
  ticketBook: Ticket = {
    id: null,
    price: null,
    start: '',
    end: '',
    startDay: '',
    startHours: '',
    garage: this.garageDefault,
    quality: null,
    status: false
  };

  constructor(private _ticketService: TickService,
              private _garageService: GaraService,
              private _formBuilder:FormBuilder,
              private _toastService: ToastrService) { }

  ngOnInit(): void {
    if(this._ticketService.message) {
      this.showMessage(this._ticketService.message);
      this._ticketService.message = '';
    }
    this._garageService.findAll().subscribe(data => {
      this.garages = data;
    });
    this.rfSearch = this._formBuilder.group({
      start:[''],
      end:[''],
      startDay:[''],
      endDay:[''],
    });
    this.goToPage(0);
  }
  showMessage(mess: string) {
    this._toastService.success(mess, '', {
      timeOut: 1000,
      positionClass: 'toast-top-right',
      easing: 'ease-in',
      progressBar: true
    });
  }

  bookTicket(id: number) {
    this._ticketService.findTicketByid(id).subscribe(data => {
      data.quality = data.quality - 1;
      this._ticketService.book(data).subscribe(value => {
        this.showMessage('Đặt vé thành công');
        document.getElementById('close-modal-book').click();
        this.goToPage(this.pageTickets.number);
      });
    });
  }

  /**
   * Gán thông tin cho ticketBook từ ticket tìm theo id
   * @param id
   */
  setValue(id: number) {
    this._ticketService.findTicketByid(id).subscribe(data => {
      this.ticketBook = data;
    });
  }

  /**
   * Gán lại giá trị hai mốc ngày(ngày bắt đầu, ngày kết thúc)
   * Ngày khởi hành sẽ nằm trong mốc ngày này
   * @private
   */
  private setDay() {
    if(!this.rfSearch.value.startDay) {
      this.rfSearch.value.startDay = '0001-01-01';
    }
    if(!this.rfSearch.value.endDay) {
      this.rfSearch.value.endDay = '9999-12-31';
    }
  }

  /**
   * Đi đến page với pageNumber tuương ứng, có kết hợp search
   * @param pageNumber: vị trí trang hiện tại
   */
  goToPage(pageNumber: number) {
    this.setDay();
    this._ticketService.getPageTicket(pageNumber, this.rfSearch.value).subscribe(data => {
      console.log(data);
      console.log(data.content);
      this.pageTickets = data;
    }, error => {
      console.log('error:',  error);
    });
  }
}

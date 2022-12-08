import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Gara} from '../model/gara';
import {TickService} from '../service/tick.service';
import {GaraService} from '../service/gara.service';
import {ToastrService} from 'ngx-toastr';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-ticket-delete',
  templateUrl: './ticket-delete.component.html',
  styleUrls: ['./ticket-delete.component.css']
})
export class TicketDeleteComponent implements OnInit {

  rfTicket: FormGroup;
  garages: Gara[];
  ticketId: number;
  garageDefault: Gara = {
    id: 1,
    name: "garage1"
  }

  compareGarage(o1: Gara, o2: Gara) {
    return o1.id == o2.id;
  };

  constructor(private _ticketService: TickService,
              private _garageService: GaraService,
              private _formBuilder:FormBuilder,
              private _toastService: ToastrService,
              private _router: Router,
              private _routerActived: ActivatedRoute) { }

  ngOnInit(): void {
    this._garageService.findAll().subscribe(data => {
      this.garages = data;
      this.ticketId = this._routerActived.snapshot.params.id;
      this._ticketService.findTicketByid(this.ticketId).subscribe(data => {
        this.rfTicket = this._formBuilder.group({
          id: [data.id],
          price:[data.price],
          start: [data.start],
          end: [data.end],
          startDay: [data.startDay],
          startHours: [data.startHours],
          garage: [data.garage],
          quality: [data.quality]
        });
      });
    });
  }

  deleteTicket() {
    this._ticketService.remove(this.rfTicket.value.id).subscribe(data => {
      this._ticketService.message = 'Xóa vé xe thành công';
      this._router.navigateByUrl('/');
    });
  }
}

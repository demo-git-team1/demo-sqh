import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TickService} from '../service/tick.service';
import {GaraService} from '../service/gara.service';
import {Gara} from '../model/gara';
import {ActivatedRoute, Router} from '@angular/router';
import {Ticket} from '../model/ticket';

@Component({
  selector: 'app-ticket-edit',
  templateUrl: './ticket-edit.component.html',
  styleUrls: ['./ticket-edit.component.css']
})
export class TicketEditComponent implements OnInit {

  rfTicket: FormGroup;
  garages: Gara[];
  ticketId: number;

  compareGarage(o1: Gara, o2: Gara) {
    return o1.id == o2.id;
  };

  constructor(private _ticketService: TickService,
              private _garageService: GaraService,
              private _formBuilder: FormBuilder,
              private _router: Router,
              private _routerActived: ActivatedRoute) {
  }

  ngOnInit(): void {
    this._garageService.findAll().subscribe(data => {
      this.garages = data;
    });
    this.ticketId = this._routerActived.snapshot.params.id;
    this._ticketService.findTicketByid(this.ticketId).subscribe(data => {
      this.rfTicket = this._formBuilder.group({
        id: [data.id],
        price: [
          data.price,
          [
            Validators.required,
            Validators.min(0),
            Validators.max(1000000),
            Validators.pattern('^\\d+\\.*\\d*$')
          ]
        ],
        start: [
          data.start,
          [
            Validators.required,
            Validators.minLength(3),
            Validators.maxLength(30)
          ]
        ],
        end: [
          data.end,
          [
            Validators.required,
            Validators.minLength(3),
            Validators.maxLength(30)
          ]
        ],
        startDay: [
          data.startDay,
          [
            Validators.required,
            Validators.pattern('^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$')
          ]
        ],
        startHours: [
          data.startHours,
          [
            Validators.required,
            Validators.pattern('^([01]?[0-9]|2[0-3]):[0-5][0-9]$'),
          ]
        ],
        garage: [data.garage],
        quality: [
          data.quality,
          [
            Validators.required,
            Validators.min(0),
            Validators.max(1000),
            Validators.pattern('^\\d*$'),
          ]
        ]
      });
    });
  }

  updateTicket() {
    this._ticketService.edit(this.rfTicket.value).subscribe(data => {
      this._ticketService.message = 'Cập nhật vé xe thành công.';
      this._router.navigateByUrl('/');
    }, error => {
      console.log('loi', error);
    });
  }
}

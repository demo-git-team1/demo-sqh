import { Component, OnInit } from '@angular/core';
import {TickService} from '../service/tick.service';
import {GaraService} from '../service/gara.service';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {Gara} from '../model/gara';
import {Router} from '@angular/router';
import {any} from 'codelyzer/util/function';

@Component({
  selector: 'app-ticket-add',
  templateUrl: './ticket-add.component.html',
  styleUrls: ['./ticket-add.component.css']
})
export class TicketAddComponent implements OnInit {

  rfTicket: FormGroup;
  errorBackEnd: any[];
  garages: Gara[];
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
              private _router: Router) { }

  ngOnInit(): void {
    this._garageService.findAll().subscribe(data => {
      this.garages = data;
      this.rfTicket = this._formBuilder.group({
        id: [],
        price:['',
          [
            Validators.required,
            Validators.min(0),
            Validators.max(1000000),
            Validators.pattern('^\\d+\\.*\\d*$')
          ]
        ],
        start: ['',
          [
            Validators.required,
            Validators.minLength(3),
            Validators.maxLength(30)
          ]
        ],
        end: ['',
          [
            Validators.required,
            Validators.minLength(3),
            Validators.maxLength(30)

          ]
        ],
        startDay: ['',
          [
            Validators.required,
            Validators.pattern('^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$')
          ]
        ],
        startHours: ['',
          [
            Validators.required,
            Validators.pattern('^([01]?[0-9]|2[0-3]):[0-5][0-9]$'),
          ]
        ],
        garage: [this.garageDefault],
        quality: ['',
          [
            Validators.required,
            Validators.min(0),
            Validators.max(1000),
            Validators.pattern('^\\d*$'),
          ]
        ]
      }, {validators: [this.checkStartDay, this.checkStartHours]});
    });
  }

  checkStartDay: ValidatorFn = (controls: AbstractControl): ValidationErrors | null => {
    const startDay = controls.get('startDay').value;
    const nowDay = new Date().toDateString();
    if(new Date(startDay).getTime() < new Date(nowDay).getTime()) {
      return {checkStartDay: true};
    }
    return  null;
  }

  checkStartHours: ValidatorFn = (controls: AbstractControl): ValidationErrors | null => {
    if(controls.get('startHours').value) {
      const startHours = controls.get('startHours').value.split(':');
      const now = new Date();
      const hh = now.getHours();
      const mm = now.getMinutes();
      if(startHours[0] < hh) {
        return {checkStartHours: true}
      } else if (startHours[0] == hh && startHours[1] < mm){
        return {checkStartHours: true}
      }
    }
    return  null;
  }
  createTicket() {
    this._ticketService.create(this.rfTicket.value).subscribe(data => {
      console.log('data-add:', data);
      this._ticketService.message = 'Thêm mới vé xe thành công';
      this._router.navigateByUrl('/');
    }, error => {
      this.errorBackEnd = error.error;
      console.log(this.errorBackEnd);
    });
  }

  removeErrorBackEnd() {
    this.errorBackEnd = [];
  }
}

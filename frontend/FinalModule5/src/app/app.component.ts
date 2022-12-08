import { Component } from '@angular/core';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'FinalModule5';
  // names = ['A', 'B', 'C', 'D'];
  // p: string | number;
  // tslint:disable-next-line:variable-name
  constructor() {
  }
  // showMessage() {
  //   this._toastService.success('Hello World', 'Welcome', {
  //     timeOut: 1000,
  //     positionClass: 'toast-top-right',
  //     easing: 'ease-in',
  //     progressBar: true
  //   });
  // }
  // http://localhost:3000/students?_sort=point&_order=desc&_limit=2
}

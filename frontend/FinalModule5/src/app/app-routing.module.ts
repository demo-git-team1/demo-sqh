import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TicketComponent} from './ticket/ticket.component';
import {TicketEditComponent} from './ticket-edit/ticket-edit.component';
import {TicketAddComponent} from './ticket-add/ticket-add.component';
import {TicketDeleteComponent} from './ticket-delete/ticket-delete.component';


const routes: Routes = [
  {path: '', component: TicketComponent},
  {path: 'ticket-edit/:id', component: TicketEditComponent},
  {path: 'ticket-add', component: TicketAddComponent},
  {path: 'ticket-delete/:id', component: TicketDeleteComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

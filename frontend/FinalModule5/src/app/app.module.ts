import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';
import {NgxPaginationModule} from 'ngx-pagination';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { TicketComponent } from './ticket/ticket.component';
import { TicketEditComponent } from './ticket-edit/ticket-edit.component';
import { TicketAddComponent } from './ticket-add/ticket-add.component';
import { TicketDeleteComponent } from './ticket-delete/ticket-delete.component';

@NgModule({
  declarations: [
    AppComponent,
    TicketComponent,
    TicketEditComponent,
    TicketAddComponent,
    TicketDeleteComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxPaginationModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

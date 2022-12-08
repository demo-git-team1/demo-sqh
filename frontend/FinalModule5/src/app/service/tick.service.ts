import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Ticket} from '../model/ticket';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {TicketComponent} from '../ticket/ticket.component';

@Injectable({
  providedIn: 'root'
})
export class TickService {

  message = '';
  constructor(private _httpClient: HttpClient) { }

  create(ticket: any): Observable<Ticket> {
    return  this._httpClient.post<Ticket>(environment.URL_API_TICKET, ticket);
  }

  findTicketByid(id: number): Observable<Ticket> {
    return  this._httpClient.get(environment.URL_API_TICKET + '/' + id);
  }

  book(data: Ticket): Observable<Ticket> {
    return this._httpClient.put(environment.URL_API_TICKET + '/'+ data.id, data);
  }

  search(rfSearch: any): Observable<Ticket[]> {
    return  this._httpClient.get<Ticket[]>(environment.URL_API_TICKET +
      '/search?start=' + rfSearch.start +
      '&end=' + rfSearch.end +
      '&startDay=' + rfSearch.startDay +
      '&endDay=' + rfSearch.endDay
    );
  }
  edit(value: any): Observable<Ticket> {
    return this._httpClient.put(environment.URL_API_TICKET + '/' + value.id, value);
  }

  remove(id: any): Observable<void> {
    return this._httpClient.delete<void>(environment.URL_API_TICKET + '/' + id);
  }
  getPageTicket(pageNumber: number, rfSearch: any): Observable<any>   {
    return this._httpClient.get<any>(environment.URL_API_TICKET +
      '/pg?page=' + pageNumber +
      '&start=' + rfSearch.start +
      '&end=' + rfSearch.end +
      '&startDay=' + rfSearch.startDay +
      '&endDay=' + rfSearch.endDay
    );
  }
}

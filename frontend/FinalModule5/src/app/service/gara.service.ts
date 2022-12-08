import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Gara} from '../model/gara';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GaraService {

  constructor(private _httpClient: HttpClient) { }

  findAll(): Observable<Gara[]> {
    return  this._httpClient.get<Gara[]>(environment.URL_API_GARAGE);
  }
}

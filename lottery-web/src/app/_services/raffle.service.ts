import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppConstants } from 'app/common/app.constants';
import { RaffleValues } from 'app/response/raffle-values';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RaffleService {

  constructor(private http: HttpClient) { }

  public save(raffle:RaffleValues):Observable<RaffleValues>{
    return this.http.post<RaffleValues>(AppConstants.API_RAFFLE, raffle);
  }

}

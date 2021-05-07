import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppConstants } from 'app/common/app.constants';
import { RaffleNumbers } from 'app/response/raffle-numbers';
import { RaffleValues } from 'app/response/raffle-values';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RaffleService {

  constructor(private http: HttpClient) { }
/*
  public save(raffle:RaffleValues):Observable<RaffleValues>{
    return this.http.post<RaffleValues>(AppConstants.API_RAFFLE, raffle);
  }
*/
  public save(formData:FormData):Observable<RaffleValues>{
    return this.http.post<RaffleValues>(AppConstants.API_RAFFLE, formData);
  }
  public saveImage(formData:FormData):Observable<RaffleValues>{
    return this.http.post<RaffleValues>(AppConstants.API_RAFFLE+'image', formData);
  }

  public getAll():Observable<RaffleValues[]>{
    return this.http.get<RaffleValues[]>(AppConstants.API_RAFFLE+'all');
  }

  public getAllActive():Observable<RaffleValues[]>{
    return this.http.get<RaffleValues[]>(AppConstants.API_RAFFLE+'all/active');
  }

  public getOneComplete(id:number):Observable<RaffleValues>{
    return this.http.get<RaffleValues>(AppConstants.API_RAFFLE+'one/complete/' + id);
  }

  public update(raffle:RaffleValues):Observable<RaffleValues>{
    return this.http.put<RaffleValues>(AppConstants.API_RAFFLE, raffle);
  }

  public updateImage(id:number, formData:FormData):Observable<RaffleValues>{
    return this.http.post<RaffleValues>(AppConstants.API_RAFFLE + 'image/' + id, formData);
  }

  public getByStatus(id:number, status:string):Observable<RaffleNumbers[]>{
    return this.http.get<RaffleNumbers[]>(AppConstants.API_RAFFLE+'list/' + id + '/' + status);
  }

  public assign(raffle:RaffleNumbers):Observable<any>{
    return this.http.put<any>(AppConstants.API_RAFFLE+'assign/'+raffle.raffleId+'/'+raffle.number,httpOptions);
  }

  public updateStatus(id:number, raffleNumber:number):Observable<any>{
    return this.http.get<any>(AppConstants.API_RAFFLE+'status/'+id+'/'+raffleNumber);
  }

}

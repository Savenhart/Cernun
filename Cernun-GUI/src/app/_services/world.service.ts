import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class WorldService {

  constructor(private http: HttpClient) { }

  create(name: string, seed: number) {
    return this.http.post<any>(`${environment.apiUrl}/world/create`, {name, seed}).pipe(map(res => {
      if (res.statusHttp === 200) {
        console.log(res);
      }
      return res;
  }));
  }

  getAll() {
    return this.http.get<any>(`${environment.apiUrl}/world`);
}
}

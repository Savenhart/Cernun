import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import { World } from '../_models/world.model';

@Injectable({
  providedIn: 'root'
})
export class WorldService {

  constructor(private http: HttpClient) { }

  create(name: string, seed: number) {
    return this.http.post<any>(`${environment.apiUrl}/world/create`, { name, seed }).pipe(map(res => {
      if (res.statusHttp === 200) {

      }
      return res;
    }));
  }

  getAll() {
    return this.http.get<any>(`${environment.apiUrl}/world`).pipe(map(res => {
      const worldList: World[] = [];
      for (const w of res.content) {
        worldList.push(new World(w));
      }
      console.log(worldList);
      return worldList;
    }));
  }

  delete(id: number) {
    return this.http.delete<any>(`${environment.apiUrl}/world/${id}`).pipe(map(res => {
      return res;
    }));
  }
}

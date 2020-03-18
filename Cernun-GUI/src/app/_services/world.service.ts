import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import { World } from '../_models/world.model';
import { Location } from '../_models/location.model';
import { Cell } from '../_models/cell.model';

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
      return worldList;
    }));
  }

  delete(id: number) {
    return this.http.delete<any>(`${environment.apiUrl}/world/${id}`).pipe(map(res => {
      return res;
    }));
  }

  getOrGenerateWorldCell(id: number, pos: Location, scale: number) {
    const posX = pos.posX;
    const posY = pos.posY;
    return this.http.post<any>(`${environment.apiUrl}/world/grid/${id}/${scale}`, {posX, posY}).pipe(map(res => {
      const gridCell = new Set<Cell>();
      for (const c of res.content) {
        gridCell.add(new Cell(c));
      }
      return gridCell;
    }));
  }
}

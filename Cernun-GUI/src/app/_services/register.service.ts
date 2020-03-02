import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../_models/user.model';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  private newUserSubject: BehaviorSubject<User>;
  public newUser: Observable<User>;

  constructor(private http: HttpClient) {
    this.newUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('newUser')));
    this.newUser = this.newUserSubject.asObservable();
  }

  register(accountName: string, password: string, passwordVerif: string, userName: string) {
    return this.http.post<any>(`${environment.apiUrl}/user/create`, { accountName, password, userName })
      .pipe(map(user => {
        if (user.statusHttp === 200) {
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.newUserSubject.next(user.content);
        }
        return user;
      }));
  }

  verifyName(name: string) {
    return this.http.get<any>(`${environment.apiUrl}/user/verify/${name}`)
    .pipe(map(user => {
      if (user.statusHttp === 200) {
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.newUserSubject.next(user.content);
      }
      return user;
    }));
  }

}

import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../_models/user.model';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import * as sha512 from 'js-sha512';

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

  register(email: string, password: string, userName: string) {
    password  = sha512.sha512('CernunosPassword' + password);
    return this.http.post<any>(`${environment.apiUrl}/user/create`, { email, password, userName })
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
      return user;
    }));
  }

}

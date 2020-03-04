import { Component, OnInit } from '@angular/core';
import { UserService } from '../../_services/user.service';
import { first } from 'rxjs/operators';
import { User } from 'src/app/_models/user.model';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {
  userList: User[];
  error: string;
  success: string;
  user: User;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getAll()
      .subscribe(
        data => {
          this.userList = [];
          for (const u of data.content) {
            this.user = new User(u.id, u.accountName, u.userName, u.password);
            this.userList.push(this.user);
          }

        },
        error => {

        });
  }

  onClickSuppr(id: number) {
    this.userService.delete(id)
      .pipe(first())
      .subscribe(
        data => {
          this.success = data.content;
          this.userService.getAll()
            .pipe(first())
            .subscribe(
              data2 => {
                this.userList = [];
                for (const u of data2.content) {
                  this.user = new User(u.id, u.accountName, u.userName, u.password);
                  this.userList.push(this.user);
                }
              },
              error2 => {

              });
        },
        error => {
          this.error = error;

        });
  }

}

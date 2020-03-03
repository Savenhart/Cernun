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

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getAll()
      .pipe(first())
      .subscribe(
        data => {
          this.userList = data.content;
        },
        error => {
          console.log(error);
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
                this.userList = data2.content;
              },
              error2 => {
                console.log(error2);
              });
        },
        error => {
          this.error = error;
          console.log(error);
        });
  }

}

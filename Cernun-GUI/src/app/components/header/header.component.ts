import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../_services/authentication.service';
import { Router } from '@angular/router';
import { User } from '../../_models/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  currentUser: User;
  auth: AuthenticationService;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
}

  ngOnInit(): void {
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
}
}

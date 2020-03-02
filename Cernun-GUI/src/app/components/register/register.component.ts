import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { first } from 'rxjs/operators';
import { RegisterService } from '../../_services/register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  @Input() fieldvalue = '';
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  error = '';

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private authenticationService: AuthenticationService,
              private registerService: RegisterService) {
    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      accountName: ['', Validators.required],
      password: ['', Validators.required],
      passwordVerif: ['', Validators.required],
      userName: ['', Validators.required]
    });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  // convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;
    if (this.f.password.value === this.f.passwordVerif.value) {
      this.registerService.register(this.f.accountName.value, this.f.password.value, this.f.passwordVerif.value, this.f.userName.value)
        .pipe(first())
        .subscribe(
          data => {
            if (data.statusHttp === 200) {
              this.router.navigate([this.returnUrl]);
            } else {
              this.error = data.error;
              this.loading = false;
            }
          },
          error => {
            this.error = error;
            this.loading = false;
          });
    } else {
      this.error = 'Les mots de passes sont différents.';
      this.loading = false;
    }
  }

  keyup(event) {
    console.log(event);
    if (event !== '') {
      this.registerService.verifyName(event).pipe(first())
        .subscribe(
          data => {
            if (data.statusHttp === 200) {
              this.error = '';
            } else {
              this.error = data.error;
              this.loading = false;
            }
          },
          error => {
            this.error = error;
            this.loading = false;
          });
    }

  }
}

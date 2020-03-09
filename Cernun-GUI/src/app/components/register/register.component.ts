import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { first } from 'rxjs/operators';
import { RegisterService } from '../../_services/register.service';
import { RegisterValidator } from 'src/app/validators/register-validator.validator';

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
  email: FormControl;
  password: FormControl;
  passwordVerif: FormControl;
  userName: FormControl;

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
    this.email = new FormControl('', [Validators.required, Validators.email]),
    this.userName = new FormControl('', Validators.required),
    this.password = new FormControl('', [Validators.required, Validators.minLength(8)]),
    this.passwordVerif = new FormControl('', [Validators.required]),
    this.registerForm = this.formBuilder.group({
        emailFc: this.email,
        passwordFc: this.password,
        passwordVerifFc: this.passwordVerif,
        userNameFc: this.userName,
      },
        {
          validators:
            [RegisterValidator.mustMatch(),
            RegisterValidator.mustDiff()]
        }
      );

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  controlEmail(): string | false {
    if (this.email.touched) {

      if (this.email.hasError('required')) {
        return `Le nom de compte est requis`;
      }
      if (this.registerForm.hasError('must_diff')) {
        return `Le nom de compte doit être différent de celui d'utilisateur`;
      }
      if (this.email.hasError('email')) {
        return `Vous devez entrer une adresse email valide`;
      }
    }
    return false;
  }

  controlUserName(): string | false {
    if (this.userName.touched) {

      if (this.userName.hasError('required')) {
        return `Le nom d'utilisateur est requis`;
      }
      if (this.registerForm.hasError('must_diff')) {
        return `Le nom d'utilisateur doit être différent de celui du compte`;
      }
    }
    return false;
  }

  controlPassword(): string | false {
    if (this.password.touched) {

      if (this.password.hasError('minlength')) {
        return `Le mot de passe doit faire 8 charactère minimum`;
      }

      if (this.password.hasError('required')) {
        return `Le mot de passe est requis`;
      }
    }
    return false;
  }

  controlPasswordVerif(): string | false {
    if (this.passwordVerif.touched) {
      if (this.passwordVerif.hasError('required')) {
        return `Le mot de passe est requis`;
      }
      if (this.registerForm.hasError('must_match')) {
        return `Les mots de passe sont différents`;
      }
    }
    return false;
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

    this.registerService.register(this.f.emailFc.value, this.f.passwordFc.value, this.f.userNameFc.value)
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
  }

  keyup(event) {
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

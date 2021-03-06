import { Component, OnInit } from '@angular/core';
import { WorldService } from '../../_services/world.service';
import { first } from 'rxjs/operators';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { World } from '../../_models/world.model';

@Component({
  selector: 'app-world',
  templateUrl: './world.component.html',
  styleUrls: ['./world.component.scss']
})
export class WorldComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  error = '';
  worldList: World[];
  world: World;
  success = '';

  constructor(private formBuilder: FormBuilder,
              private worldService: WorldService) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      name: ['', Validators.required],
      seed: ['', Validators.required]
    });

    this.worldService.getAll()
      .pipe(first())
      .subscribe(
        data => {
          this.worldList = data;
        },
        error => {
        });
  }

  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  createWorld() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }
    this.loading = true;

    this.worldService.create(this.f.name.value, this.f.seed.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.statusHttp === 200) {
            this.worldService.getAll()
              .pipe(first())
              .subscribe(
                data2 => {
                  this.submitted = false;
                  this.loading = false;
                  this.worldList = data2;
                },
                error2 => {

                });
          } else {
            this.error = data.error;
            this.loading = false;
          }
        },
        error => {
          this.error = error;
        });
  }

  // getAllWorld() {
  //   this.worldService.getAll();
  // }

  onClickSuppr(id: number) {
    this.worldService.delete(id)
      .pipe(first())
      .subscribe(
        data => {
          this.success = data.content;
          this.worldService.getAll()
            .pipe(first())
            .subscribe(
              data2 => {
                this.worldList = data2;
              },
              error2 => {
              });
        },
        error => {
          this.error = error;

        });
  }
}

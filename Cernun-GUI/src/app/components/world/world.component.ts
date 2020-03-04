import { Component, OnInit } from '@angular/core';
import { WorldService } from '../../_services/world.service';
import { first } from 'rxjs/operators';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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
  returnUrl: string;
  error = '';
  worldList: World[];
  world: World;
  success = '';

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private worldService: WorldService) {}



  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      name: ['', Validators.required],
      seed: ['', Validators.required]
    });

    this.worldService.getAll()
      .pipe(first())
      .subscribe(
        data => {
          this.worldList = [];
          for (const w of data.content) {
            this.world = new World(w.id, w.name, w.seed);
            this.worldList.push(this.world);
          }
        },
        error => {

        });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
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
                this.worldList = [];
                for (const w of data2.content) {
                  this.world = new World(w.id, w.name, w.seed);
                  this.worldList.push(this.world);
                  console.log(this.world);
                }
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

  getAllWorld() {
    this.worldService.getAll();
  }

  onClickSuppr(id: number) {
    console.log(id);
    this.worldService.delete(id)
      .pipe(first())
      .subscribe(
        data => {
          console.log(data);
          this.success = data.content;
          this.worldService.getAll()
            .pipe(first())
            .subscribe(
              data2 => {
                this.worldList = [];
                for (const w of data2.content) {
                  this.world = new World(w.id, w.name, w.seed);
                  this.worldList.push(this.world);
                }
              },
              error2 => {
                console.log(error2);
              });
        },
        error => {
          console.log(error);
          this.error = error;

        });
  }
}

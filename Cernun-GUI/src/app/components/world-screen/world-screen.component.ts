import { Component, OnInit } from '@angular/core';
import * as p5 from 'p5';

@Component({
  selector: 'app-world-screen',
  templateUrl: './world-screen.component.html',
  styleUrls: ['./world-screen.component.scss']
})
export class WorldScreenComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
        // tslint:disable-next-line: no-unused-expression
        new p5(p => {
          let x = 100;
          let y = 100;

          p.setup = () => {
            const cnv = p.createCanvas(p.windowWidth, p.windowHeight);
            cnv.style('display', 'block');

            cnv.parent('world');
          };

          p.draw = () => {
            p.background(0);
            p.fill(255);
            p.rect(x, y, 50, 50);
          };

          p.windowResized = () => {
            p.resizeCanvas(p.windowWidth, p.windowHeight);
          };
        });
  }

}

import { Component, OnInit } from '@angular/core';
import * as p5 from 'p5';
import { ActivatedRoute } from '@angular/router';
import { WorldService } from 'src/app/_services/world.service';
import { Location } from '../../_models/location.model';
import { first, tap } from 'rxjs/operators';
import { Cell } from '../../_models/cell.model';

@Component({
  selector: 'app-world-screen',
  templateUrl: './world-screen.component.html',
  styleUrls: ['./world-screen.component.scss']
})
export class WorldScreenComponent implements OnInit {

  id: number;
  scale: number;
  p5: p5;
  pos: Location;
  error: string;
  gridList: Set<Cell>;

  constructor(private route: ActivatedRoute,
              private worldService: WorldService) {
    this.route.queryParams.subscribe(params => {
      this.id = params.id;
    });
  }

  ngOnInit(): void {
    this.pos = new Location();
    this.gridList = new Set<Cell>();
    // let s = this.gridList;
    this.getOrGenerateWorld().subscribe(() => {



      // tslint:disable-next-line: no-unused-expression
      this.p5 = new p5(p => {
        const x = 100;
        const y = 100;

        p.setup = () => {
          const cnv = p.createCanvas(p.windowWidth / 2, p.windowHeight / 2, p.WEBGL);
          cnv.style('display', 'block');
          cnv.class('img-responsive');


          cnv.parent('world');
        };

        p.draw = () => {
          p.background(255);
          for (const elem of this.gridList) {
            p.fill(Math.floor(Math.random() * (256)), Math.floor(Math.random() * (256)),Math.floor(Math.random() * (256)));
            p.rect(elem.location.posX * 25, elem.location.posY * 25, 25, 25);
          }
        };
        //Math.floor(Math.random() * (256)), Math.floor(Math.random() * (256)),Math.floor(Math.random() * (256))
        p.windowResized = () => {
          p.resizeCanvas(p.windowWidth / 2, p.windowHeight / 2);
        };
      });
    });
  }

  getOrGenerateWorld(x?, y?, scale?) {
    this.pos.posX = x || 0;
    this.pos.posY = y || 0;
    this.scale = scale || 5;
    return this.worldService.getOrGenerateWorldCell(this.id, this.pos, this.scale)
      .pipe(tap(data => { this.gridList = data; }));
  }
}

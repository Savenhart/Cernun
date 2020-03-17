import { Component, OnInit, OnDestroy } from '@angular/core';
import * as p5 from 'p5';
import { ActivatedRoute } from '@angular/router';
import { WorldService } from 'src/app/_services/world.service';
import { Location } from '../../_models/location.model';
import { first, tap, takeUntil } from 'rxjs/operators';
import { Cell } from '../../_models/cell.model';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-world-screen',
  templateUrl: './world-screen.component.html',
  styleUrls: ['./world-screen.component.scss']
})
export class WorldScreenComponent implements OnInit, OnDestroy {

  private readonly onDestroy = new Subject<void>();

  id: number;
  scale: number;
  p5: p5;
  pos: Location;
  error: string;
  gridList: Set<Cell>;
  originX = 0;
  originY = 0;
  size = 20;

  constructor(private route: ActivatedRoute,
              private worldService: WorldService) {
    this.route.queryParams.subscribe(params => {
      this.id = params.id;
      this.pos = new Location();
      this.gridList = new Set<Cell>();
      this.getOrGenerateWorld(this.originX, this.originY).pipe(takeUntil(this.onDestroy)).subscribe(() => { });
    });
  }

  ngOnDestroy(): void {
    this.onDestroy.next();
  }

  ngOnInit(): void {
      // tslint:disable-next-line: no-unused-expression
    this.p5 = new p5(p => {

      p.setup = () => {
          const cnv = p.createCanvas(p.windowWidth / 2, p.windowHeight * 0.75, p.WEBGL);
          p.frameRate(10);
          cnv.style('display', 'block');
          cnv.class('img-responsive');


          cnv.parent('world');
        };

      p.draw = () => {
        // console.log(p.getFrameRate());
        const angle = p.TWO_PI / 6;

        p.background(125);
        for (const elem of this.gridList) {
              p.fill(elem.biome.path);
              const cX = this.originX + elem.location.posX;
              const cy = this.originY + elem.location.posY;
              p.translate(cX * 3 * this.size / 2 , cy * p.sqrt(3) * this.size + p.abs(cX) % 2 * p.sqrt(3) * 20 / 2);
              p.beginShape();
              p.noStroke();
              for (let a = 0; a < p.TWO_PI; a += angle) {
              const hx = p.cos(a) * this.size;
              const hy = p.sin(a) * this.size;
              p.vertex(hx, hy);
            }
              p.endShape(p.CLOSE);
              p.translate(-cX * 3 * this.size / 2 , -(cy * p.sqrt(3) * this.size + p.abs(cX) % 2 * p.sqrt(3) * 20 / 2));
          }


        if ((p.keyIsPressed === true) && (p.key === 'z') || (p.key === 'Z')) {
          this.originY--;
        }
      };

      p.windowResized = () => {
        p.resizeCanvas(p.windowWidth / 2,  p.windowHeight * 0.75);
      };
    });
  }

  getOrGenerateWorld(x?, y?, scale?) {
    this.pos.posX = x || 0;
    this.pos.posY = y || 0;
    this.scale = scale || 5;
    this.gridList.clear();
    // .pipe(tap()) return an Observable
    return this.worldService.getOrGenerateWorldCell(this.id, this.pos, this.scale)
      .pipe(tap(data => { this.gridList = data; }));
  }

}

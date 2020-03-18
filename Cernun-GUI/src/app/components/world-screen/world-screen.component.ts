import { Component, OnInit, OnDestroy } from '@angular/core';
import * as p5 from 'p5';
import { ActivatedRoute } from '@angular/router';
import { WorldService } from 'src/app/_services/world.service';
import { Location } from '../../_models/location.model';
import { tap, takeUntil } from 'rxjs/operators';
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
  scale = 5;
  p5: p5;
  pos: Location;
  error: string;
  gridList: Set<Cell>;
  originX = 0;
  originY = 0;
  size = 20;
  goUp = false;
  goDown = false;
  goLeft = false;
  goRight = false;
  nbCells: number;
  pas: number;
  zoomMax = 10;
  zoomMin = 5;

  constructor(private route: ActivatedRoute,
              private worldService: WorldService) {
    this.route.queryParams.subscribe(params => {
      this.id = params.id;
      this.pos = new Location();
      this.gridList = new Set<Cell>();
      this.getOrGenerateWorld(this.originX, this.originY, this.scale).pipe(takeUntil(this.onDestroy)).subscribe(() => { });
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

        this.pas = (this.zoomMax - this.zoomMin) / this.scale;
        this.nbCells = this.zoomMin / this.pas;
      };

      p.draw = () => {
        const angle = p.TWO_PI / 6;
        // moving map with the keyboard
        if (this.goUp || this.goDown || this.goLeft || this.goRight) {
          this.originX += ((this.goLeft ? 1 : 0) - (this.goRight ? 1 : 0)) * 2;
          this.originY += ((this.goDown ? 1 : 0) - (this.goUp ? 1 : 0)) * 2;
        }

        if (this.gridList.size !== 0) {
          p.background(20);
          p.translate(-this.originX * this.size * 3 / 2,
            -(this.originY * p.sqrt(3) * this.size + this.originX % 2 * p.sqrt(3) * this.size / 2));
          for (const elem of this.gridList) {
            p.fill(elem.biome.path);
            const cX = elem.location.posX * 3 * this.size / 2;
            const cy = elem.location.posY * p.sqrt(3) * this.size + p.abs(elem.location.posX) % 2 * p.sqrt(3) * this.size / 2;
            p.translate(cX, cy);
            p.beginShape();
            p.noStroke();
            for (let a = 0; a < p.TWO_PI; a += angle) {
              const hx = p.cos(a) * this.size;
              const hy = p.sin(a) * this.size;
              p.vertex(hx, hy);
            }
            p.endShape(p.CLOSE);
            p.translate(-cX, -cy);
          }
          p.translate(this.originX * this.size * 3 / 2,
            this.originY * p.sqrt(3) * this.size + this.originX % 2 * p.sqrt(3) * this.size / 2);
        }
      };

      p.keyPressed = () => {
        this.nbCells = parseInt(this.nbCells.toFixed(0));
        this.pas = parseInt(this.pas.toFixed(0));
        switch (p.key) {
          case '-':
            this.nbCells = this.nbCells < this.zoomMax ? this.nbCells += this.pas : this.zoomMax;
            this.scale = this.scale < this.zoomMax ? this.scale + 1 : this.zoomMax;
            break;
          case '+':
            this.nbCells = this.nbCells > this.zoomMin ? this.nbCells - this.pas : this.zoomMin;
            this.scale = this.scale > this.zoomMin ? this.scale -1 : this.zoomMin;
            break;
          case 'z':
            this.goUp = true;
            break;
          case 'q':
            this.goLeft = true;
            break;
          case 's':
            this.goDown = true;
            break;
          case 'd':
            this.goRight = true;
            break;
        }
        console.log(this.scale);
      };

      p.keyReleased = () => {
        switch (p.key) {
          case 'z':
            this.goUp = false;
            break;
          case 'q':
            this.goLeft = false;
            break;
          case 's':
            this.goDown = false;
            break;
          case 'd':
            this.goRight = false;
            break;
        }
        console.log("test");
        this.getOrGenerateWorld(this.originX, this.originY, this.scale).pipe(takeUntil(this.onDestroy)).subscribe(() => { });
      };


      p.windowResized = () => {
        p.resizeCanvas(p.windowWidth / 2, p.windowHeight * 0.75);
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

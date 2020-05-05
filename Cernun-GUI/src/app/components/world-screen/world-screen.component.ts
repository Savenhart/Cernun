import { Component, OnInit, OnDestroy } from '@angular/core';
import * as p5 from 'p5';
import { ActivatedRoute } from '@angular/router';
import { WorldService } from 'src/app/_services/world.service';
import { Location } from '../../_models/location.model';
import { tap, takeUntil } from 'rxjs/operators';
import { Cell } from '../../_models/cell.model';
import { Subject, Observable } from 'rxjs';
import { Food } from 'src/app/_models/food.model';
import { environment } from '../../../environments/environment';
import * as Stomp from 'stompjs';
import { Creature } from 'src/app/_models/creature.model';


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
  foodList: Set<Food>;
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
  socket: any;
  listCreature: Creature[];

  constructor(private route: ActivatedRoute,
              private worldService: WorldService) {
    this.route.queryParams.subscribe(params => {
      this.id = params.id;
      this.pos = new Location();
      this.gridList = new Set<Cell>();
      this.foodList = new Set<Food>();
      this.getOrGenerateWorld(this.originX, this.originY, this.scale).pipe(takeUntil(this.onDestroy)).subscribe(() => { });
      this.getWorldFood(this.originX, this.originY, this.scale).pipe(takeUntil(this.onDestroy)).subscribe(() => { });
    });
    // websocket
    const webSocket = new WebSocket(environment.wsUrl);
    this.socket = Stomp.over(webSocket);
    const that = this;
    this.socket.connect({}, (frame) => {
      that.socket.pipe(takeUntil(that.onDestroy)).subscribe('/errors', (message) => {
        alert('Error' + message.body);
      });
      that.socket.pipe(takeUntil(that.onDestroy)).subscribe('/creature', (message) => {
        that.listCreature = [];
        for (const c of message.body) {
          that.listCreature.push(new Creature(c));
        }
      });
      const currentUser = localStorage.getItem('currentUser');
      that.socket.send('/app/connect/' + that.id, {}, currentUser);
    }, (error) => {
      alert('STOMP error' + error);
    });
  }

  ngOnDestroy(): void {
    this.onDestroy.next();
    const currentUser = localStorage.getItem('currentUser');
    this.socket.send('/app/disconnect/' + this.id, {}, currentUser);
    if (this.socket != null) {
      this.socket.ws.close();
    }
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
          this.originX += ((this.goRight ? 1 : 0) - (this.goLeft ? 1 : 0)) * 2;
          this.originY += ((this.goDown ? 1 : 0) - (this.goUp ? 1 : 0)) * 2;
        }

        // affichage cellule
        if (this.gridList.size !== 0) {
          p.background(20);
          p.translate(-this.originX * this.size * 3 / 2,
            -(this.originY * p.sqrt(3) * this.size + this.originX % 2 * p.sqrt(3) * this.size / 2));
          for (const elem of this.gridList) {
            p.fill(elem.biome.path);
            const cX = elem.location.posX * 3 * this.size / 2;
            const cY = elem.location.posY * p.sqrt(3) * this.size + p.abs(elem.location.posX) % 2 * p.sqrt(3) * this.size / 2;
            p.translate(cX, cY);
            p.beginShape();
            p.noStroke();
            for (let a = 0; a < p.TWO_PI; a += angle) {
              const hx = p.cos(a) * this.size;
              const hy = p.sin(a) * this.size;
              p.vertex(hx, hy);
            }
            p.endShape(p.CLOSE);
            p.translate(-cX, -cY);
          }

          p.translate(this.originX * this.size * 3 / 2,
            this.originY * p.sqrt(3) * this.size + this.originX % 2 * p.sqrt(3) * this.size / 2);
        }

        // affichage nourriture
        if (this.foodList.size !== 0) {
          p.translate(-this.originX * this.size * 3 / 2,
            -(this.originY * p.sqrt(3) * this.size + this.originX % 2 * p.sqrt(3) * this.size / 2));
          for (const elem of this.foodList) {
            p.fill(0);
            const cX = elem.location.posX * 3 * this.size / 2;
            const cY = elem.location.posY * p.sqrt(3) * this.size + p.abs(elem.location.posX) % 2 * p.sqrt(3) * this.size / 2;
            p.translate(cX, cY);
            p.stroke(255);
            p.circle(0, 0, this.size);
            p.translate(-cX, -cY);
          }
          p.translate(this.originX * this.size * 3 / 2,
            this.originY * p.sqrt(3) * this.size + this.originX % 2 * p.sqrt(3) * this.size / 2);
        }

        // affichage creature
        if (this.listCreature.length !== 0) {
          p.translate(-this.originX * this.size * 3 / 2,
            -(this.originY * p.sqrt(3) * this.size + this.originX % 2 * p.sqrt(3) * this.size / 2));
          for (const elem of this.listCreature) {
            p.fill('red');
            const cX = elem.posX * 3 * this.size / 2;
            const cY = elem.posY * p.sqrt(3) * this.size + p.abs(elem.posX) % 2 * p.sqrt(3) * this.size / 2;
            p.translate(cX, cY);
            p.stroke(255);
            p.circle(0, 0, this.size);
            p.translate(-cX, -cY);
          }
          p.translate(this.originX * this.size * 3 / 2,
            this.originY * p.sqrt(3) * this.size + this.originX % 2 * p.sqrt(3) * this.size / 2);
        }
      };

      p.keyPressed = () => {
        this.nbCells = parseInt(this.nbCells.toFixed(0), 10);
        this.pas = parseInt(this.pas.toFixed(0), 10);
        switch (p.key) {
          case '-':
            this.nbCells = this.nbCells < this.zoomMax ? this.nbCells += this.pas : this.zoomMax;
            this.scale = this.scale < this.zoomMax ? this.scale + 1 : this.zoomMax;
            break;
          case '+':
            this.nbCells = this.nbCells > this.zoomMin ? this.nbCells - this.pas : this.zoomMin;
            this.scale = this.scale > this.zoomMin ? this.scale - 1 : this.zoomMin;
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
        this.getOrGenerateWorld(this.originX, this.originY, this.scale).pipe(takeUntil(this.onDestroy)).subscribe(() => { });
        this.getWorldFood(this.originX, this.originY, this.scale).pipe(takeUntil(this.onDestroy)).subscribe(() => { });
      };

      p.windowResized = () => {
        p.resizeCanvas(p.windowWidth / 2, p.windowHeight * 0.75);
      };
    });
  }

  getOrGenerateWorld(x, y, scale): Observable<Set<Cell>> {
    this.pos.posX = x;
    this.pos.posY = y;
    this.scale = scale;
    this.gridList.clear();
    // .pipe(tap()) return an Observable
    return this.worldService.getOrGenerateWorldCell(this.id, this.pos, this.scale)
      .pipe(tap(data => { this.gridList = data; }));
  }

  getWorldFood(x, y, scale): Observable<Set<Food>> {
    this.pos.posX = x;
    this.pos.posY = y;
    this.scale = scale;
    this.foodList.clear();
    return this.worldService.getWorldFood(this.id, this.pos, this.scale)
      .pipe(tap(data => { this.foodList = data; }));
  }

}

export class Creature{
    
    constructor(x, y, color, ctx){
        this._x = x;
        this._y = y;
        this._color = color;
        this._ctx = ctx;
        this.ctx.fillStyle = this.color;
        this.ctx.fillRect(this.x, this.y, 30, 30);
    }

    get x(){
        return this._x;
    }

    set x(x){
        this._x = x;
    }

    get y(){
        return this._x;
    }

    set y(y){
        this._y = y;
    }

    get color(){
        return this._color;
    }

    set color(color){
        this._color = color;
    }

    get ctx(){
        return this._ctx;
    }

    set ctx(ctx){
        this._ctx = ctx;
    }

    clearRect() {
        this.ctx.save();
        this.ctx.clearRect(0,0,800, 600);
        this.ctx.fillStyle = this.color;
        this.ctx.fillRect(this.x, this.y, 30, 30);
        this.ctx.restore();
      }
}
export class Food{

    constructor(x, y, color, ctx){
        this._x = x;
        this._y = y;
        this._color = color;
        this._ctx = ctx;
        this._ctx.beginPath();
        this._ctx.fillStyle = this._color;
        this._ctx.arc(this.x, this.y, 5, 0, 2 * Math.PI);
        this._ctx.fill();
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

    clearArc() {
        this._ctx.save();
        //this._ctx.globalCompositeOperation = 'destination-out';
        this._ctx.clearRect(0,0,800, 600);
        this._ctx.beginPath();
        this._ctx.fillStyle = this._color;
        this._ctx.arc(this._x, this._y, 5, 0, 2 * Math.PI);
        this._ctx.fill();
        this._ctx.restore();
      }
}
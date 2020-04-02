export class Location {
  posX: number;
  posY: number;

  constructor(obj?: object) {
    if (!obj) {
      this.posX = 0;
      this.posY = 0;
    } else {
      Object.assign(this, obj);
    }

  }
}

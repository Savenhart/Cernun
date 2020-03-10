export class Location {
  posX: number;
  posY: number;

  constructor(obj: object) {
    Object.assign(this, obj);
  }
}

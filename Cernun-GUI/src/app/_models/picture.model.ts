export class Picture {
  name: string;
  ipath: string;
  extension: string;

  constructor(obj: object) {
    Object.assign(this, obj);
  }
}

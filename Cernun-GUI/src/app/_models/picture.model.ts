export class Picture {
  id: number;
  name: string;
  ipath: string;
  extension: string;

  constructor(obj: object) {
    Object.assign(this, obj);
  }
}

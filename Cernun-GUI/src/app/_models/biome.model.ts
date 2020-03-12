export class Biome {
  biome: string;
  path: string;

  constructor(obj: object) {
    Object.assign(this, obj);
  }
}

import { World } from './world.model';
import { Location } from './location.model';
import { Biome } from './biome.model';
import { Picture } from './picture.model';

export class Cell {
  world: World;
  location: Location;
  biome: Biome;
  picture: Picture;

  constructor(obj: object) {
    Object.assign(this, obj);
  }
}

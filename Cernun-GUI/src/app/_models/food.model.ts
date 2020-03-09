import { World } from './world.model';
import { Picture } from './picture.model';
import { Location } from './location.model';
export class Food {
  id: number;
  world: World;
  energy: number;
  isMeat: boolean;
  picture: Picture;
  location: Location;

  constructor(obj: object) {
    Object.assign(this, obj);
  }
}

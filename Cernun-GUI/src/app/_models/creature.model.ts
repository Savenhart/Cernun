import { Picture } from './picture.model';
import { UserWorld } from './user-world.model';

export class Creature {
  name: string;
  speed: number;
  energy: number;
  energyMax: number;
  perception: number;
  mass: number;
  diet: number;
  ratioSeaMountain: number;
  posX: number;
  posY: number;
  picture: Picture;
  userWorld: UserWorld;

  constructor(obj: object) {
    Object.assign(this, obj);
  }
}

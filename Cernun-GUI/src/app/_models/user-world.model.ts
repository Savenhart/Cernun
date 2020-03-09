import { World } from './world.model';
import { User } from './user.model';
import { Creature } from './creature.model';
export class UserWorld {
  id: number;
  world: World;
  user: User;
  creatures: Set<Creature>;

  constructor(obj: object) {
    Object.assign(this, obj);
  }
}

import { World } from './world.model';
import { User } from './user.model';
export class Droit {
  id: number;
  world: World;
  user: User;
  isAdmin: boolean;

  constructor(obj: object) {
    Object.assign(this, obj);
  }
}

import { Picture } from './picture.model';
import { UserWorld } from './user-world.model';
import { Droit } from './droit.model';
export class User {
  id: number;
  email: string;
  userName: string;
  password: string;
  avatar: Picture;
  userWorld: Set<UserWorld>;
  droit: Set<Droit>;
  token?: string;

  constructor(obj: object) {
    Object.assign(this, obj);
  }
}

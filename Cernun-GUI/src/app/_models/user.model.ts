import { Picture } from './picture.model';
export class User {
  id: number;
  accountName: string;
  userName: string;
  password: string;
  avatar: Picture;
  // appartenances: Set<Appartenance>;
  token?: string;

  constructor(obj: object) {
    Object.assign(this, obj);
  }
}

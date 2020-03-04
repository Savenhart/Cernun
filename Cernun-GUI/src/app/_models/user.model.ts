import { Picture } from './picture.model';
export class User {
  id: number;
  accountName: string;
  userName: string;
  password: string;
  avatar: Picture;
  // appartenances: Set<Appartenance>;
  token?: string;

  constructor(id: number, accountName: string, userName: string, password: string, token?: string, avatar?: Picture) {
    this.id = id;
    this.accountName = accountName;
    this.userName = userName;
    this.password = password;
    this.token = token || '';
    this.avatar = avatar || null;
  }

}

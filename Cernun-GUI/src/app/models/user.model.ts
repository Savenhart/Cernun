export class User {
  private _name: string;
  private _pseudo: string;
  private _password: string;
  // private _avatar: picture;
  // private _appartenances: Set<Appartenance>;

  /**
   * Getter name
   * @return string
   */
  public get name(): string {
    return this._name;
  }

  /**
   * Setter name
   * @param string value
   */
  public set name(value: string) {
    this._name = value;
  }

  /**
   * Getter pseudo
   * @return string
   */
  public get pseudo(): string {
    return this._pseudo;
  }

  /**
   * Setter pseudo
   * @param string value
   */
  public set pseudo(value: string) {
    this._pseudo = value;
  }

  /**
   * Getter password
   * @return string
   */
  public get password(): string {
    return this._password;
  }

  /**
   * Setter password
   * @param string value
   */
  public set password(value: string) {
    this._password = value;
  }

}

import { Cell } from './cell.model';

export class World {
  id: number;
  name: string;
  seed: number;
  cellsSet: Set<Cell>;

  constructor(obj: object) {
    Object.assign(this, obj);
  }
}

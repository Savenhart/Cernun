import { Cell } from './cell.model';

export class World {
  id: number;
  name: string;
  seed: number;
  cellsSet: Set<Cell>;

  constructor(id: number, name: string, seed: number, cellsSet?: Set<Cell>) {
    this.id = id;
    this.name = name;
    this.seed = seed;
    this.cellsSet = cellsSet || new Set<Cell>();
  }
}

import { Cell } from './cell.model';

export class World {
  name: string;
  seed: number;
  cellsSet: Set<Cell>;
}

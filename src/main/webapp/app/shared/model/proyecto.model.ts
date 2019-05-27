export interface IProyecto {
  id?: number;
  descripcion?: string;
}

export class Proyecto implements IProyecto {
  constructor(public id?: number, public descripcion?: string) {}
}

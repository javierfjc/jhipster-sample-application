export interface ICliente {
  id?: number;
  descripcion?: string;
}

export class Cliente implements ICliente {
  constructor(public id?: number, public descripcion?: string) {}
}

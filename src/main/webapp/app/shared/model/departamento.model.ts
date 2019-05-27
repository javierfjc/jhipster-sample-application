export interface IDepartamento {
  id?: number;
  descripcion?: string;
}

export class Departamento implements IDepartamento {
  constructor(public id?: number, public descripcion?: string) {}
}

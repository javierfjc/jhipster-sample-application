export interface IPerfil {
  id?: number;
  titulo?: string;
  descripcion?: string;
  nivel?: number;
}

export class Perfil implements IPerfil {
  constructor(public id?: number, public titulo?: string, public descripcion?: string, public nivel?: number) {}
}

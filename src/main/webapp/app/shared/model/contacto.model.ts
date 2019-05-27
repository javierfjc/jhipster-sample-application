export interface IContacto {
  id?: number;
  nombre?: string;
  email?: string;
  telefono?: string;
  perfil?: string;
  clienteId?: number;
}

export class Contacto implements IContacto {
  constructor(
    public id?: number,
    public nombre?: string,
    public email?: string,
    public telefono?: string,
    public perfil?: string,
    public clienteId?: number
  ) {}
}

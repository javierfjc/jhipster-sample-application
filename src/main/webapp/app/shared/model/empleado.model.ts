import { Moment } from 'moment';

export interface IEmpleado {
  id?: number;
  nombre?: string;
  email?: string;
  telefono?: string;
  antiguedad?: Moment;
  salario?: number;
  comision?: number;
}

export class Empleado implements IEmpleado {
  constructor(
    public id?: number,
    public nombre?: string,
    public email?: string,
    public telefono?: string,
    public antiguedad?: Moment,
    public salario?: number,
    public comision?: number
  ) {}
}

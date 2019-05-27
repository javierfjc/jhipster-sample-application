import { Moment } from 'moment';

export const enum TareasEstado {
  ASIGNADO = 'ASIGNADO',
  INICIADO = 'INICIADO',
  PENDIENTE = 'PENDIENTE',
  APLAZADO = 'APLAZADO',
  FINALIZADO = 'FINALIZADO',
  APROBADO = 'APROBADO',
  INSTALADO = 'INSTALADO'
}

export interface ITarea {
  id?: number;
  descripcion?: string;
  estado?: TareasEstado;
  fechaCreado?: Moment;
  fechaPrevistoInicio?: Moment;
  fechaInicio?: Moment;
  fechaFinal?: Moment;
  horasPrevisto?: number;
}

export class Tarea implements ITarea {
  constructor(
    public id?: number,
    public descripcion?: string,
    public estado?: TareasEstado,
    public fechaCreado?: Moment,
    public fechaPrevistoInicio?: Moment,
    public fechaInicio?: Moment,
    public fechaFinal?: Moment,
    public horasPrevisto?: number
  ) {}
}

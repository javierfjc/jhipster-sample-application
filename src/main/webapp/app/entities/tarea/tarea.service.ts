import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITarea } from 'app/shared/model/tarea.model';

type EntityResponseType = HttpResponse<ITarea>;
type EntityArrayResponseType = HttpResponse<ITarea[]>;

@Injectable({ providedIn: 'root' })
export class TareaService {
  public resourceUrl = SERVER_API_URL + 'api/tareas';

  constructor(protected http: HttpClient) {}

  create(tarea: ITarea): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarea);
    return this.http
      .post<ITarea>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tarea: ITarea): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarea);
    return this.http
      .put<ITarea>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITarea>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITarea[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tarea: ITarea): ITarea {
    const copy: ITarea = Object.assign({}, tarea, {
      fechaCreado: tarea.fechaCreado != null && tarea.fechaCreado.isValid() ? tarea.fechaCreado.toJSON() : null,
      fechaPrevistoInicio:
        tarea.fechaPrevistoInicio != null && tarea.fechaPrevistoInicio.isValid() ? tarea.fechaPrevistoInicio.toJSON() : null,
      fechaInicio: tarea.fechaInicio != null && tarea.fechaInicio.isValid() ? tarea.fechaInicio.toJSON() : null,
      fechaFinal: tarea.fechaFinal != null && tarea.fechaFinal.isValid() ? tarea.fechaFinal.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaCreado = res.body.fechaCreado != null ? moment(res.body.fechaCreado) : null;
      res.body.fechaPrevistoInicio = res.body.fechaPrevistoInicio != null ? moment(res.body.fechaPrevistoInicio) : null;
      res.body.fechaInicio = res.body.fechaInicio != null ? moment(res.body.fechaInicio) : null;
      res.body.fechaFinal = res.body.fechaFinal != null ? moment(res.body.fechaFinal) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tarea: ITarea) => {
        tarea.fechaCreado = tarea.fechaCreado != null ? moment(tarea.fechaCreado) : null;
        tarea.fechaPrevistoInicio = tarea.fechaPrevistoInicio != null ? moment(tarea.fechaPrevistoInicio) : null;
        tarea.fechaInicio = tarea.fechaInicio != null ? moment(tarea.fechaInicio) : null;
        tarea.fechaFinal = tarea.fechaFinal != null ? moment(tarea.fechaFinal) : null;
      });
    }
    return res;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ITarea, Tarea } from 'app/shared/model/tarea.model';
import { TareaService } from './tarea.service';

@Component({
  selector: 'jhi-tarea-update',
  templateUrl: './tarea-update.component.html'
})
export class TareaUpdateComponent implements OnInit {
  tarea: ITarea;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    descripcion: [],
    estado: [],
    fechaCreado: [],
    fechaPrevistoInicio: [],
    fechaInicio: [],
    fechaFinal: [],
    horasPrevisto: []
  });

  constructor(protected tareaService: TareaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tarea }) => {
      this.updateForm(tarea);
      this.tarea = tarea;
    });
  }

  updateForm(tarea: ITarea) {
    this.editForm.patchValue({
      id: tarea.id,
      descripcion: tarea.descripcion,
      estado: tarea.estado,
      fechaCreado: tarea.fechaCreado != null ? tarea.fechaCreado.format(DATE_TIME_FORMAT) : null,
      fechaPrevistoInicio: tarea.fechaPrevistoInicio != null ? tarea.fechaPrevistoInicio.format(DATE_TIME_FORMAT) : null,
      fechaInicio: tarea.fechaInicio != null ? tarea.fechaInicio.format(DATE_TIME_FORMAT) : null,
      fechaFinal: tarea.fechaFinal != null ? tarea.fechaFinal.format(DATE_TIME_FORMAT) : null,
      horasPrevisto: tarea.horasPrevisto
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tarea = this.createFromForm();
    if (tarea.id !== undefined) {
      this.subscribeToSaveResponse(this.tareaService.update(tarea));
    } else {
      this.subscribeToSaveResponse(this.tareaService.create(tarea));
    }
  }

  private createFromForm(): ITarea {
    const entity = {
      ...new Tarea(),
      id: this.editForm.get(['id']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      estado: this.editForm.get(['estado']).value,
      fechaCreado:
        this.editForm.get(['fechaCreado']).value != null ? moment(this.editForm.get(['fechaCreado']).value, DATE_TIME_FORMAT) : undefined,
      fechaPrevistoInicio:
        this.editForm.get(['fechaPrevistoInicio']).value != null
          ? moment(this.editForm.get(['fechaPrevistoInicio']).value, DATE_TIME_FORMAT)
          : undefined,
      fechaInicio:
        this.editForm.get(['fechaInicio']).value != null ? moment(this.editForm.get(['fechaInicio']).value, DATE_TIME_FORMAT) : undefined,
      fechaFinal:
        this.editForm.get(['fechaFinal']).value != null ? moment(this.editForm.get(['fechaFinal']).value, DATE_TIME_FORMAT) : undefined,
      horasPrevisto: this.editForm.get(['horasPrevisto']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarea>>) {
    result.subscribe((res: HttpResponse<ITarea>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

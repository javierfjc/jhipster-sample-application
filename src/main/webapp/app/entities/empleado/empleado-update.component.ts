import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IEmpleado, Empleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from './empleado.service';

@Component({
  selector: 'jhi-empleado-update',
  templateUrl: './empleado-update.component.html'
})
export class EmpleadoUpdateComponent implements OnInit {
  empleado: IEmpleado;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    email: [],
    telefono: [],
    antiguedad: [],
    salario: [],
    comision: []
  });

  constructor(protected empleadoService: EmpleadoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ empleado }) => {
      this.updateForm(empleado);
      this.empleado = empleado;
    });
  }

  updateForm(empleado: IEmpleado) {
    this.editForm.patchValue({
      id: empleado.id,
      nombre: empleado.nombre,
      email: empleado.email,
      telefono: empleado.telefono,
      antiguedad: empleado.antiguedad != null ? empleado.antiguedad.format(DATE_TIME_FORMAT) : null,
      salario: empleado.salario,
      comision: empleado.comision
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const empleado = this.createFromForm();
    if (empleado.id !== undefined) {
      this.subscribeToSaveResponse(this.empleadoService.update(empleado));
    } else {
      this.subscribeToSaveResponse(this.empleadoService.create(empleado));
    }
  }

  private createFromForm(): IEmpleado {
    const entity = {
      ...new Empleado(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      email: this.editForm.get(['email']).value,
      telefono: this.editForm.get(['telefono']).value,
      antiguedad:
        this.editForm.get(['antiguedad']).value != null ? moment(this.editForm.get(['antiguedad']).value, DATE_TIME_FORMAT) : undefined,
      salario: this.editForm.get(['salario']).value,
      comision: this.editForm.get(['comision']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpleado>>) {
    result.subscribe((res: HttpResponse<IEmpleado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

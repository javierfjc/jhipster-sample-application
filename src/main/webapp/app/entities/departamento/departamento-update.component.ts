import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDepartamento, Departamento } from 'app/shared/model/departamento.model';
import { DepartamentoService } from './departamento.service';

@Component({
  selector: 'jhi-departamento-update',
  templateUrl: './departamento-update.component.html'
})
export class DepartamentoUpdateComponent implements OnInit {
  departamento: IDepartamento;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    descripcion: [null, [Validators.required]]
  });

  constructor(protected departamentoService: DepartamentoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ departamento }) => {
      this.updateForm(departamento);
      this.departamento = departamento;
    });
  }

  updateForm(departamento: IDepartamento) {
    this.editForm.patchValue({
      id: departamento.id,
      descripcion: departamento.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const departamento = this.createFromForm();
    if (departamento.id !== undefined) {
      this.subscribeToSaveResponse(this.departamentoService.update(departamento));
    } else {
      this.subscribeToSaveResponse(this.departamentoService.create(departamento));
    }
  }

  private createFromForm(): IDepartamento {
    const entity = {
      ...new Departamento(),
      id: this.editForm.get(['id']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartamento>>) {
    result.subscribe((res: HttpResponse<IDepartamento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

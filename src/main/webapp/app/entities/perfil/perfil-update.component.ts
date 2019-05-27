import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPerfil, Perfil } from 'app/shared/model/perfil.model';
import { PerfilService } from './perfil.service';

@Component({
  selector: 'jhi-perfil-update',
  templateUrl: './perfil-update.component.html'
})
export class PerfilUpdateComponent implements OnInit {
  perfil: IPerfil;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    titulo: [],
    descripcion: [],
    nivel: []
  });

  constructor(protected perfilService: PerfilService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ perfil }) => {
      this.updateForm(perfil);
      this.perfil = perfil;
    });
  }

  updateForm(perfil: IPerfil) {
    this.editForm.patchValue({
      id: perfil.id,
      titulo: perfil.titulo,
      descripcion: perfil.descripcion,
      nivel: perfil.nivel
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const perfil = this.createFromForm();
    if (perfil.id !== undefined) {
      this.subscribeToSaveResponse(this.perfilService.update(perfil));
    } else {
      this.subscribeToSaveResponse(this.perfilService.create(perfil));
    }
  }

  private createFromForm(): IPerfil {
    const entity = {
      ...new Perfil(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      nivel: this.editForm.get(['nivel']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfil>>) {
    result.subscribe((res: HttpResponse<IPerfil>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IContacto, Contacto } from 'app/shared/model/contacto.model';
import { ContactoService } from './contacto.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';

@Component({
  selector: 'jhi-contacto-update',
  templateUrl: './contacto-update.component.html'
})
export class ContactoUpdateComponent implements OnInit {
  contacto: IContacto;
  isSaving: boolean;

  clientes: ICliente[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    email: [],
    telefono: [],
    perfil: [],
    clienteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected contactoService: ContactoService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contacto }) => {
      this.updateForm(contacto);
      this.contacto = contacto;
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(contacto: IContacto) {
    this.editForm.patchValue({
      id: contacto.id,
      nombre: contacto.nombre,
      email: contacto.email,
      telefono: contacto.telefono,
      perfil: contacto.perfil,
      clienteId: contacto.clienteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contacto = this.createFromForm();
    if (contacto.id !== undefined) {
      this.subscribeToSaveResponse(this.contactoService.update(contacto));
    } else {
      this.subscribeToSaveResponse(this.contactoService.create(contacto));
    }
  }

  private createFromForm(): IContacto {
    const entity = {
      ...new Contacto(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      email: this.editForm.get(['email']).value,
      telefono: this.editForm.get(['telefono']).value,
      perfil: this.editForm.get(['perfil']).value,
      clienteId: this.editForm.get(['clienteId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContacto>>) {
    result.subscribe((res: HttpResponse<IContacto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }
}

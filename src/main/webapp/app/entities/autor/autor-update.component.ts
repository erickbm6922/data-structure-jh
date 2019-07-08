import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAutor, Autor } from 'app/shared/model/autor.model';
import { AutorService } from './autor.service';
import { ILibro } from 'app/shared/model/libro.model';
import { LibroService } from 'app/entities/libro';

@Component({
  selector: 'jhi-autor-update',
  templateUrl: './autor-update.component.html'
})
export class AutorUpdateComponent implements OnInit {
  isSaving: boolean;

  libros: ILibro[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    correo: [],
    libros: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected autorService: AutorService,
    protected libroService: LibroService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ autor }) => {
      this.updateForm(autor);
    });
    this.libroService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILibro[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILibro[]>) => response.body)
      )
      .subscribe((res: ILibro[]) => (this.libros = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(autor: IAutor) {
    this.editForm.patchValue({
      id: autor.id,
      nombre: autor.nombre,
      correo: autor.correo,
      libros: autor.libros
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const autor = this.createFromForm();
    if (autor.id !== undefined) {
      this.subscribeToSaveResponse(this.autorService.update(autor));
    } else {
      this.subscribeToSaveResponse(this.autorService.create(autor));
    }
  }

  private createFromForm(): IAutor {
    return {
      ...new Autor(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      correo: this.editForm.get(['correo']).value,
      libros: this.editForm.get(['libros']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutor>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
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

  trackLibroById(index: number, item: ILibro) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}

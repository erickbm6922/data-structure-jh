import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ILibro, Libro } from 'app/shared/model/libro.model';
import { LibroService } from './libro.service';

@Component({
  selector: 'jhi-libro-update',
  templateUrl: './libro-update.component.html'
})
export class LibroUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    titulo: [],
    descripcion: [],
    precio: []
  });

  constructor(protected libroService: LibroService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ libro }) => {
      this.updateForm(libro);
    });
  }

  updateForm(libro: ILibro) {
    this.editForm.patchValue({
      id: libro.id,
      titulo: libro.titulo,
      descripcion: libro.descripcion,
      precio: libro.precio
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const libro = this.createFromForm();
    if (libro.id !== undefined) {
      this.subscribeToSaveResponse(this.libroService.update(libro));
    } else {
      this.subscribeToSaveResponse(this.libroService.create(libro));
    }
  }

  private createFromForm(): ILibro {
    return {
      ...new Libro(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      precio: this.editForm.get(['precio']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILibro>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

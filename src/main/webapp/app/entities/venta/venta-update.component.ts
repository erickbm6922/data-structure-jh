import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IVenta, Venta } from 'app/shared/model/venta.model';
import { VentaService } from './venta.service';

@Component({
  selector: 'jhi-venta-update',
  templateUrl: './venta-update.component.html'
})
export class VentaUpdateComponent implements OnInit {
  isSaving: boolean;
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    folio: [],
    fecha: [],
    subtotal: [],
    impuesto: [],
    total: []
  });

  constructor(protected ventaService: VentaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ venta }) => {
      this.updateForm(venta);
    });
  }

  updateForm(venta: IVenta) {
    this.editForm.patchValue({
      id: venta.id,
      folio: venta.folio,
      fecha: venta.fecha,
      subtotal: venta.subtotal,
      impuesto: venta.impuesto,
      total: venta.total
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const venta = this.createFromForm();
    if (venta.id !== undefined) {
      this.subscribeToSaveResponse(this.ventaService.update(venta));
    } else {
      this.subscribeToSaveResponse(this.ventaService.create(venta));
    }
  }

  private createFromForm(): IVenta {
    return {
      ...new Venta(),
      id: this.editForm.get(['id']).value,
      folio: this.editForm.get(['folio']).value,
      fecha: this.editForm.get(['fecha']).value,
      subtotal: this.editForm.get(['subtotal']).value,
      impuesto: this.editForm.get(['impuesto']).value,
      total: this.editForm.get(['total']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenta>>) {
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

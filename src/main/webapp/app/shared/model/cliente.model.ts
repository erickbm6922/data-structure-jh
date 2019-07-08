import { IVenta } from 'app/shared/model/venta.model';

export interface ICliente {
  id?: number;
  razonSocial?: string;
  correo?: string;
  ventas?: IVenta[];
}

export class Cliente implements ICliente {
  constructor(public id?: number, public razonSocial?: string, public correo?: string, public ventas?: IVenta[]) {}
}

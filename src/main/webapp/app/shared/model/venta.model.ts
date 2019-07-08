import { Moment } from 'moment';

export interface IVenta {
  id?: number;
  folio?: string;
  fecha?: Moment;
  subtotal?: number;
  impuesto?: number;
  total?: number;
}

export class Venta implements IVenta {
  constructor(
    public id?: number,
    public folio?: string,
    public fecha?: Moment,
    public subtotal?: number,
    public impuesto?: number,
    public total?: number
  ) {}
}

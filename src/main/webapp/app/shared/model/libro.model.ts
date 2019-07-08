export interface ILibro {
  id?: number;
  titulo?: string;
  descripcion?: string;
  precio?: number;
}

export class Libro implements ILibro {
  constructor(public id?: number, public titulo?: string, public descripcion?: string, public precio?: number) {}
}

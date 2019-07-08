import { ILibro } from 'app/shared/model/libro.model';

export interface IAutor {
  id?: number;
  nombre?: string;
  correo?: string;
  libros?: ILibro[];
}

export class Autor implements IAutor {
  constructor(public id?: number, public nombre?: string, public correo?: string, public libros?: ILibro[]) {}
}

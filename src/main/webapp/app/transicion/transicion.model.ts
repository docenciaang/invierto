export class TransicionDTO {

  constructor(data:Partial<TransicionDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  monto?: number|null;
  fecha?: string|null;
  detalle?: string|null;
  origneId?: number|null;
  destinoId?: number|null;
  tipo?: string|null;

}

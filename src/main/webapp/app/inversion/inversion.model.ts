export class InversionDTO {

  constructor(data:Partial<InversionDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  monto?: number|null;
  fechaInversion?: string|null;
  fechaVencimiento?: string|null;
  tasaInteres?: number|null;
  nombreFondo?: string|null;
  valorActual?: number|null;
  tipo?: string|null;
  bancoId?: number|null;
  archivado?: number|null;

}

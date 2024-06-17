export class CuentaDTO {

  constructor(data:Partial<CuentaDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  numeroCuenta?: string|null;
  saldo?: number|null;
  fechaCreacion?: string|null;
  bancoId?: number|null;

}

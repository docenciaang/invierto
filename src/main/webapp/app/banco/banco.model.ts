export class BancoDTO {

  constructor(data:Partial<BancoDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  nombre?: string|null;

}

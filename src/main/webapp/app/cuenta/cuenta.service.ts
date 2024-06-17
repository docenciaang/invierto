import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { CuentaDTO } from 'app/cuenta/cuenta.model';


@Injectable({
  providedIn: 'root',
})
export class CuentaService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/cuentas';

  getAllCuentas() {
    return this.http.get<CuentaDTO[]>(this.resourcePath);
  }

  getCuenta(id: number) {
    return this.http.get<CuentaDTO>(this.resourcePath + '/' + id);
  }

  createCuenta(cuentaDTO: CuentaDTO) {
    return this.http.post<number>(this.resourcePath, cuentaDTO);
  }

  updateCuenta(id: number, cuentaDTO: CuentaDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, cuentaDTO);
  }

  deleteCuenta(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

}

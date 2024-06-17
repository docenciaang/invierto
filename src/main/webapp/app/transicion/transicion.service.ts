import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { TransicionDTO } from 'app/transicion/transicion.model';


@Injectable({
  providedIn: 'root',
})
export class transaccioneservice {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/transacciones';

  getAlltransacciones() {
    return this.http.get<TransicionDTO[]>(this.resourcePath);
  }

  getTransicion(id: number) {
    return this.http.get<TransicionDTO>(this.resourcePath + '/' + id);
  }

  createTransicion(transicionDTO: TransicionDTO) {
    return this.http.post<number>(this.resourcePath, transicionDTO);
  }

  updateTransicion(id: number, transicionDTO: TransicionDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, transicionDTO);
  }

  deleteTransicion(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

}

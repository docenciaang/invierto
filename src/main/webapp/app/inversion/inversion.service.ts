import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { InversionDTO } from 'app/inversion/inversion.model';


@Injectable({
  providedIn: 'root',
})
export class InversionService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/inversions';

  getAllInversions() {
    return this.http.get<InversionDTO[]>(this.resourcePath);
  }

  getInversion(id: number) {
    return this.http.get<InversionDTO>(this.resourcePath + '/' + id);
  }

  createInversion(inversionDTO: InversionDTO) {
    return this.http.post<number>(this.resourcePath, inversionDTO);
  }

  updateInversion(id: number, inversionDTO: InversionDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, inversionDTO);
  }

  deleteInversion(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

}

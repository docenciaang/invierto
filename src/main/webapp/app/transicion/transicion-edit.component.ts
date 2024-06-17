import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { transaccioneservice } from 'app/transicion/transicion.service';
import { TransicionDTO } from 'app/transicion/transicion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm, validDouble } from 'app/common/utils';


@Component({
  selector: 'app-transicion-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './transicion-edit.component.html'
})
export class TransicionEditComponent implements OnInit {

  transaccioneservice = inject(transaccioneservice);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  currentId?: number;

  editForm = new FormGroup({
    id: new FormControl({ value: null, disabled: true }),
    monto: new FormControl(null, [validDouble]),
    fecha: new FormControl(null, [Validators.maxLength(255)]),
    detalle: new FormControl(null, [Validators.maxLength(255)]),
    origneId: new FormControl(null),
    destinoId: new FormControl(null),
    tipo: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@transicion.update.success:Transicion was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentId = +this.route.snapshot.params['id'];
    this.transaccioneservice.getTransicion(this.currentId!)
        .subscribe({
          next: (data) => updateForm(this.editForm, data),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.editForm.markAllAsTouched();
    if (!this.editForm.valid) {
      return;
    }
    const data = new TransicionDTO(this.editForm.value);
    this.transaccioneservice.updateTransicion(this.currentId!, data)
        .subscribe({
          next: () => this.router.navigate(['/transacciones'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}

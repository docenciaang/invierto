import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { transaccioneservice } from 'app/transicion/transicion.service';
import { TransicionDTO } from 'app/transicion/transicion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { validDouble } from 'app/common/utils';


@Component({
  selector: 'app-transicion-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './transicion-add.component.html'
})
export class TransicionAddComponent {

  transaccioneservice = inject(transaccioneservice);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  addForm = new FormGroup({
    monto: new FormControl(null, [validDouble]),
    fecha: new FormControl(null, [Validators.maxLength(255)]),
    detalle: new FormControl(null, [Validators.maxLength(255)]),
    origneId: new FormControl(null),
    destinoId: new FormControl(null),
    tipo: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@transicion.create.success:Transicion was created successfully.`
    };
    return messages[key];
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new TransicionDTO(this.addForm.value);
    this.transaccioneservice.createTransicion(data)
        .subscribe({
          next: () => this.router.navigate(['/transacciones'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}

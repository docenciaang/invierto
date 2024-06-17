import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CuentaService } from 'app/cuenta/cuenta.service';
import { CuentaDTO } from 'app/cuenta/cuenta.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { validDouble } from 'app/common/utils';


@Component({
  selector: 'app-cuenta-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './cuenta-add.component.html'
})
export class CuentaAddComponent {

  cuentaService = inject(CuentaService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  addForm = new FormGroup({
    numeroCuenta: new FormControl(null, [Validators.maxLength(255)]),
    saldo: new FormControl(null, [validDouble]),
    fechaCreacion: new FormControl(null),
    bancoId: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@cuenta.create.success:Cuenta was created successfully.`
    };
    return messages[key];
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new CuentaDTO(this.addForm.value);
    this.cuentaService.createCuenta(data)
        .subscribe({
          next: () => this.router.navigate(['/cuentas'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}

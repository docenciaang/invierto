import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CuentaService } from 'app/cuenta/cuenta.service';
import { CuentaDTO } from 'app/cuenta/cuenta.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm, validDouble } from 'app/common/utils';


@Component({
  selector: 'app-cuenta-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './cuenta-edit.component.html'
})
export class CuentaEditComponent implements OnInit {

  cuentaService = inject(CuentaService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  currentId?: number;

  editForm = new FormGroup({
    id: new FormControl({ value: null, disabled: true }),
    numeroCuenta: new FormControl(null, [Validators.maxLength(255)]),
    saldo: new FormControl(null, [validDouble]),
    fechaCreacion: new FormControl(null),
    bancoId: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@cuenta.update.success:Cuenta was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentId = +this.route.snapshot.params['id'];
    this.cuentaService.getCuenta(this.currentId!)
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
    const data = new CuentaDTO(this.editForm.value);
    this.cuentaService.updateCuenta(this.currentId!, data)
        .subscribe({
          next: () => this.router.navigate(['/cuentas'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}

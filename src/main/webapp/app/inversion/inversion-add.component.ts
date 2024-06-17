import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { InversionService } from 'app/inversion/inversion.service';
import { InversionDTO } from 'app/inversion/inversion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { validDouble } from 'app/common/utils';


@Component({
  selector: 'app-inversion-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './inversion-add.component.html'
})
export class InversionAddComponent {

  inversionService = inject(InversionService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  addForm = new FormGroup({
    monto: new FormControl(null, [validDouble]),
    fechaInversion: new FormControl(null),
    fechaVencimiento: new FormControl(null),
    tasaInteres: new FormControl(null, [validDouble]),
    nombreFondo: new FormControl(null, [Validators.maxLength(255)]),
    valorActual: new FormControl(null, [validDouble]),
    tipo: new FormControl(null),
    bancoId: new FormControl(null),
    archivado: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@inversion.create.success:Inversion was created successfully.`
    };
    return messages[key];
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new InversionDTO(this.addForm.value);
    this.inversionService.createInversion(data)
        .subscribe({
          next: () => this.router.navigate(['/inversions'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}

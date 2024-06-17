import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { InversionService } from 'app/inversion/inversion.service';
import { InversionDTO } from 'app/inversion/inversion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm, validDouble } from 'app/common/utils';


@Component({
  selector: 'app-inversion-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './inversion-edit.component.html'
})
export class InversionEditComponent implements OnInit {

  inversionService = inject(InversionService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  currentId?: number;

  editForm = new FormGroup({
    id: new FormControl({ value: null, disabled: true }),
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
      updated: $localize`:@@inversion.update.success:Inversion was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentId = +this.route.snapshot.params['id'];
    this.inversionService.getInversion(this.currentId!)
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
    const data = new InversionDTO(this.editForm.value);
    this.inversionService.updateInversion(this.currentId!, data)
        .subscribe({
          next: () => this.router.navigate(['/inversions'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}

import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { CuentaService } from 'app/cuenta/cuenta.service';
import { CuentaDTO } from 'app/cuenta/cuenta.model';


@Component({
  selector: 'app-cuenta-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './cuenta-list.component.html'})
export class CuentaListComponent implements OnInit, OnDestroy {

  cuentaService = inject(CuentaService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  cuentas?: CuentaDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@cuenta.delete.success:Cuenta was removed successfully.`    };
    return messages[key];
  }

  ngOnInit() {
    this.loadData();
    this.navigationSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.loadData();
      }
    });
  }

  ngOnDestroy() {
    this.navigationSubscription!.unsubscribe();
  }
  
  loadData() {
    this.cuentaService.getAllCuentas()
        .subscribe({
          next: (data) => this.cuentas = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.cuentaService.deleteCuenta(id)
          .subscribe({
            next: () => this.router.navigate(['/cuentas'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => this.errorHandler.handleServerError(error.error)
          });
    }
  }

}

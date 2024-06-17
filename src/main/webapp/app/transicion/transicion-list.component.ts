import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { transaccioneservice } from 'app/transicion/transicion.service';
import { TransicionDTO } from 'app/transicion/transicion.model';


@Component({
  selector: 'app-transicion-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './transicion-list.component.html'})
export class TransicionListComponent implements OnInit, OnDestroy {

  transaccioneservice = inject(transaccioneservice);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  transacciones?: TransicionDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@transicion.delete.success:Transicion was removed successfully.`    };
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
    this.transaccioneservice.getAlltransacciones()
        .subscribe({
          next: (data) => this.transacciones = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.transaccioneservice.deleteTransicion(id)
          .subscribe({
            next: () => this.router.navigate(['/transacciones'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => this.errorHandler.handleServerError(error.error)
          });
    }
  }

}

import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { BancoService } from 'app/banco/banco.service';
import { BancoDTO } from 'app/banco/banco.model';


@Component({
  selector: 'app-banco-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './banco-list.component.html'})
export class BancoListComponent implements OnInit, OnDestroy {

  bancoService = inject(BancoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  bancoes?: BancoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@banco.delete.success:Banco was removed successfully.`    };
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
    this.bancoService.getAllBancoes()
        .subscribe({
          next: (data) => this.bancoes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.bancoService.deleteBanco(id)
          .subscribe({
            next: () => this.router.navigate(['/bancos'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => this.errorHandler.handleServerError(error.error)
          });
    }
  }

}

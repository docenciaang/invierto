import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { InversionService } from 'app/inversion/inversion.service';
import { InversionDTO } from 'app/inversion/inversion.model';


@Component({
  selector: 'app-inversion-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './inversion-list.component.html'})
export class InversionListComponent implements OnInit, OnDestroy {

  inversionService = inject(InversionService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  inversions?: InversionDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@inversion.delete.success:Inversion was removed successfully.`    };
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
    this.inversionService.getAllInversions()
        .subscribe({
          next: (data) => this.inversions = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.inversionService.deleteInversion(id)
          .subscribe({
            next: () => this.router.navigate(['/inversions'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => this.errorHandler.handleServerError(error.error)
          });
    }
  }

}

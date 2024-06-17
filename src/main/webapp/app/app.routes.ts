import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CuentaListComponent } from './cuenta/cuenta-list.component';
import { CuentaAddComponent } from './cuenta/cuenta-add.component';
import { CuentaEditComponent } from './cuenta/cuenta-edit.component';
import { BancoListComponent } from './banco/banco-list.component';
import { BancoAddComponent } from './banco/banco-add.component';
import { BancoEditComponent } from './banco/banco-edit.component';
import { TransicionListComponent } from './transicion/transicion-list.component';
import { TransicionAddComponent } from './transicion/transicion-add.component';
import { TransicionEditComponent } from './transicion/transicion-edit.component';
import { InversionListComponent } from './inversion/inversion-list.component';
import { InversionAddComponent } from './inversion/inversion-add.component';
import { InversionEditComponent } from './inversion/inversion-edit.component';
import { ErrorComponent } from './error/error.component';


export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: $localize`:@@home.index.headline:Welcome to your new app!`
  },
  {
    path: 'cuentas',
    component: CuentaListComponent,
    title: $localize`:@@cuenta.list.headline:Cuentas`
  },
  {
    path: 'cuentas/add',
    component: CuentaAddComponent,
    title: $localize`:@@cuenta.add.headline:Add Cuenta`
  },
  {
    path: 'cuentas/edit/:id',
    component: CuentaEditComponent,
    title: $localize`:@@cuenta.edit.headline:Edit Cuenta`
  },
  {
    path: 'bancos',
    component: BancoListComponent,
    title: $localize`:@@banco.list.headline:Bancoes`
  },
  {
    path: 'bancos/add',
    component: BancoAddComponent,
    title: $localize`:@@banco.add.headline:Add Banco`
  },
  {
    path: 'bancos/edit/:id',
    component: BancoEditComponent,
    title: $localize`:@@banco.edit.headline:Edit Banco`
  },
  {
    path: 'transacciones',
    component: TransicionListComponent,
    title: $localize`:@@transicion.list.headline:transacciones`
  },
  {
    path: 'transacciones/add',
    component: TransicionAddComponent,
    title: $localize`:@@transicion.add.headline:Add Transicion`
  },
  {
    path: 'transacciones/edit/:id',
    component: TransicionEditComponent,
    title: $localize`:@@transicion.edit.headline:Edit Transicion`
  },
  {
    path: 'inversions',
    component: InversionListComponent,
    title: $localize`:@@inversion.list.headline:Inversions`
  },
  {
    path: 'inversions/add',
    component: InversionAddComponent,
    title: $localize`:@@inversion.add.headline:Add Inversion`
  },
  {
    path: 'inversions/edit/:id',
    component: InversionEditComponent,
    title: $localize`:@@inversion.edit.headline:Edit Inversion`
  },
  {
    path: 'error',
    component: ErrorComponent,
    title: $localize`:@@error.headline:Error`
  },
  {
    path: '**',
    component: ErrorComponent,
    title: $localize`:@@notFound.headline:Page not found`
  }
];

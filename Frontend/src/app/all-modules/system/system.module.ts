import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SystemRoutingModule } from './system-routing.module';
import { SystemComponent } from './system.component';
import { MenuCreateComponent } from './components/menu/create/menu-create.component';
import { MenuEditComponent } from './components/menu/edit/menu-edit.component';
import { MenuListComponent } from './components/menu/list/menu-list.component';
import { MenuShowComponent } from './components/menu/show/menu-show.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DataTablesModule } from 'angular-datatables';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { NgxSpinnerModule } from 'ngx-spinner';


@NgModule({
  declarations: [SystemComponent, MenuCreateComponent, MenuEditComponent, MenuListComponent, MenuShowComponent],
  imports: [
    CommonModule,
    SystemRoutingModule,
    DataTablesModule,
    FormsModule,
    ReactiveFormsModule,
    NgxSpinnerModule,
    NgxPaginationModule,
    BsDatepickerModule.forRoot(),
  ]
})
export class SystemModule { }

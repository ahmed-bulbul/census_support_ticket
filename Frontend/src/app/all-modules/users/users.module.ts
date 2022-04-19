import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { UsersComponent } from './users.component';
import { DataTablesModule } from 'angular-datatables';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxSpinnerModule } from "ngx-spinner";
import { NgxPaginationModule } from 'ngx-pagination';
import { BsDatepickerModule } from 'ngx-bootstrap';

import { UsersListComponent } from './components/list/users-list.component';
import { UserEditComponent } from './components/edit/user-edit.component';
import { UserCreateComponent } from './components/create/user-create.component';
import { UserShowComponent } from './components/show/user-show.component';




@NgModule({
  declarations: [UsersComponent,UsersListComponent, UserEditComponent, UserCreateComponent,UserShowComponent],
  imports: [
    CommonModule,
    UsersRoutingModule,
    DataTablesModule,
    FormsModule,
    ReactiveFormsModule,
    NgxSpinnerModule,
    NgxPaginationModule,
    BsDatepickerModule.forRoot(),
  ]
})
export class UsersModule { }

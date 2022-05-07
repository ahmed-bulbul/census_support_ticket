import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxSpinnerModule } from 'ngx-spinner';
import { StatusComponent } from './components/status/status.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';


@NgModule({
  declarations: [StatusComponent, ChangePasswordComponent],
  imports: [
    CommonModule,
    SharedRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxSpinnerModule
  ]
})
export class SharedModule { }

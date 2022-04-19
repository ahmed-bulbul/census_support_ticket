import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductRoutingModule } from './product-routing.module';
import { ProductComponent } from './product.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DataTablesModule } from 'angular-datatables';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ProductListComponent } from './components/list/product-list.component';
import { ProductCreateComponent } from './components/create/product-create.component';
import { ProductShowComponent } from './components/show/product-show.component';
import { ProductEditComponent } from './components/edit/product-edit.component';



@NgModule({
  declarations: [ProductComponent, ProductListComponent, ProductCreateComponent, ProductShowComponent, ProductEditComponent],
  imports: [
    CommonModule,
    ProductRoutingModule,
    DataTablesModule,
    FormsModule,
    ReactiveFormsModule,
    NgxSpinnerModule,
    NgxPaginationModule,
    BsDatepickerModule.forRoot(),
  ]
})
export class ProductModule { }

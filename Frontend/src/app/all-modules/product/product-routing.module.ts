import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductCreateComponent } from './components/create/product-create.component';
import { ProductEditComponent } from './components/edit/product-edit.component';
import { ProductListComponent } from './components/list/product-list.component';
import { ProductShowComponent } from './components/show/product-show.component';
import { ProductComponent } from './product.component';

const routes: Routes = [
  {
    path:"",
    component:ProductComponent,
    children:[

      {
        path:"product/create",
        component:ProductCreateComponent
      },
      {
        path:"product/list",
        component:ProductListComponent
      },
      {
        path:"product/show/:id",
        component:ProductShowComponent
      },
      {
        path:"product/edit/:id",
        component:ProductEditComponent
      },


    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule { }

import { UserCreateComponent } from './components/create/user-create.component';

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsersComponent } from './users.component';
import { UsersListComponent } from './components/list/users-list.component';
import { UserShowComponent } from './components/show/user-show.component';
import { UserEditComponent } from './components/edit/user-edit.component';


const routes: Routes = [
  {
    path:"",
    component:UsersComponent,
    children:[

      {
        path:"user/list",
        component:UsersListComponent
      },
      {
        path:"user/show/:id",
        component:UserShowComponent
      },
      {
        path:"user/edit/:id",
        component:UserEditComponent
      },

      {
        path:"user/create",
        component:UserCreateComponent
      },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule { }

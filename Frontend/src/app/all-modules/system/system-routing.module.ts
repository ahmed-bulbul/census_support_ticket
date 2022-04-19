import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MenuGuard } from 'src/app/core/guards/menu.guard';
import { MenuCreateComponent } from './components/menu/create/menu-create.component';
import { MenuEditComponent } from './components/menu/edit/menu-edit.component';
import { MenuListComponent } from './components/menu/list/menu-list.component';
import { MenuShowComponent } from './components/menu/show/menu-show.component';
import { SystemComponent } from './system.component';

const routes: Routes = [
  {
    path:'',
    component:SystemComponent,
    children:[

      {
        path:'menu/create',
        component:MenuCreateComponent,
        canActivate:[MenuGuard]
      },
      {
        path:'menu/list',
        component:MenuListComponent,
        canActivate:[MenuGuard]
      },
      {
        path:'menu/show/:id',
        component:MenuShowComponent,
        canActivate:[MenuGuard]
      },
      {
        path:'menu/edit/:id',
        component:MenuEditComponent,
        canActivate:[MenuGuard]
      },


    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SystemRoutingModule { }

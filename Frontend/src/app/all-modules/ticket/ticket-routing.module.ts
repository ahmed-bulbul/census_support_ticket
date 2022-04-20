import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TicketComponent } from './ticket.component';

const routes: Routes = [
  {
    path:'',
    component:TicketComponent,
    children:[

      // {
      //   path:'menu/create',
      //   component:MenuCreateComponent,
      //   canActivate:[MenuGuard]
      // },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TicketRoutingModule { }

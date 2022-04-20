
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BbsGuard } from 'src/app/core/guards/bbs.guard';
import { Tire1Guard } from 'src/app/core/guards/tire1.guard';
import { TicketCreateComponent } from './components/create/ticket-create.component';
import { TicketEditComponent } from './components/edit/ticket-edit.component';
import { TicketListComponent } from './components/list/ticket-list.component';
import { TicketShowComponent } from './components/show/ticket-show.component';

import { Tire1ListComponent } from './components/tire1/list/tire1-list.component';



import { TicketComponent } from './ticket.component';

const routes: Routes = [
  {
    path:'',
    component:TicketComponent,
    children:[

      {
        path:'bbs/create',
        component:TicketCreateComponent,
        canActivate:[BbsGuard]
      },
      {
        path:'bbs/list',
        component:TicketListComponent,
        canActivate:[BbsGuard]
      },
      {
        path:'bbs/edit/:id',
        component:TicketEditComponent,
        canActivate:[BbsGuard]
      },
      {
        path:'bbs/show/:id',
        component:TicketShowComponent,
        canActivate:[BbsGuard]
      },












































      {
        path:'tire1/list',
        component:Tire1ListComponent,
        canActivate:[Tire1Guard]
      },




    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TicketRoutingModule { }

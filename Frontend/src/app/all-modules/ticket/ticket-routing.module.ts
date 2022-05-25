import { UserGuard } from './../../core/guards/user.guard';
import { Tire2ShowComponent } from './components/tire2/show/tire2-show.component';
import { Tire2ListComponent } from './components/tire2/list/tire2-list.component';

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BbsGuard } from 'src/app/core/guards/bbs.guard';
import { Tire1Guard } from 'src/app/core/guards/tire1.guard';
import { Tire2Guard } from 'src/app/core/guards/tire2.guard';
import { TicketCreateComponent } from './components/create/ticket-create.component';
import { TicketEditComponent } from './components/edit/ticket-edit.component';
import { TicketListComponent } from './components/list/ticket-list.component';
import { TicketShowComponent } from './components/show/ticket-show.component';

import { Tire1ListComponent } from './components/tire1/list/tire1-list.component';
import { Tire1ShowComponent } from './components/tire1/show/tire1-show.component';



import { TicketComponent } from './ticket.component';
import { TabletListComponent } from './components/tablet/tablet-list/tablet-list.component';


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
      {
        path:'tire1/show/:id',
        component:Tire1ShowComponent,
        canActivate:[Tire1Guard]
      },
      {
        path:'tire2/list',
        component:Tire2ListComponent,
        canActivate:[Tire2Guard]
      },
      {
        path:'tire2/show/:id',
        component:Tire2ShowComponent,
        canActivate:[Tire2Guard]
      },

      {
        path:'tablet/list',
        component:TabletListComponent,
        canActivate:[UserGuard]
      }




    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TicketRoutingModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TicketRoutingModule } from './ticket-routing.module';
import { TicketComponent } from './ticket.component';
import { TicketCreateComponent } from './components/create/ticket-create.component';
import { TicketListComponent } from './components/list/ticket-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DataTablesModule } from 'angular-datatables';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { NgxSpinnerModule } from 'ngx-spinner';

import { TicketEditComponent } from './components/edit/ticket-edit.component';
import { TicketShowComponent } from './components/show/ticket-show.component';
import { Tire1ListComponent } from './components/tire1/list/tire1-list.component';
import { Tire1ShowComponent } from './components/tire1/show/tire1-show.component';
import { Tire2ListComponent } from './components/tire2/list/tire2-list.component';
import { Tire2ShowComponent } from './components/tire2/show/tire2-show.component';





@NgModule({


  declarations: [TicketComponent, TicketCreateComponent, TicketListComponent, TicketEditComponent, TicketShowComponent,Tire1ListComponent, Tire1ShowComponent, Tire2ShowComponent, Tire2ListComponent],

  imports: [
    CommonModule,
    TicketRoutingModule,
    TicketRoutingModule,
    DataTablesModule,
    FormsModule,
    ReactiveFormsModule,
    NgxSpinnerModule,
    NgxPaginationModule,
    BsDatepickerModule.forRoot(),
  ]
})
export class TicketModule { }

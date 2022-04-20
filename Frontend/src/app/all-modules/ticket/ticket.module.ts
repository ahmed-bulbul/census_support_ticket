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



@NgModule({
  declarations: [TicketComponent, TicketCreateComponent, TicketListComponent, TicketEditComponent, TicketShowComponent],
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

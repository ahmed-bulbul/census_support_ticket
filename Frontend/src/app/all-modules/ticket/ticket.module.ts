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



@NgModule({
  declarations: [TicketComponent, TicketCreateComponent, TicketListComponent],
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

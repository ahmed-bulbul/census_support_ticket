import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { MorrisJsModule } from 'angular-morris-js';
import { CommonDashboardComponent } from './common-dashboard/common-dashboard.component';


@NgModule({
  declarations: [DashboardComponent, AdminDashboardComponent, CommonDashboardComponent],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    MorrisJsModule

  ]
})
export class DashboardModule { }

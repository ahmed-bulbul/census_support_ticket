import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { StatusComponent } from './components/status/status.component';

const routes: Routes = [
  {
    path: 'status' , component: StatusComponent
  },
  {
    path: 'change-password', component: ChangePasswordComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SharedRoutingModule { }

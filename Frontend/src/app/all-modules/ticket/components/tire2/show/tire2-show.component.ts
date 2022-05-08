import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { TicketService } from '../../../service/ticket.service';

@Component({
  selector: 'app-tire2-show',
  templateUrl: './tire2-show.component.html',
  styleUrls: ['./tire2-show.component.css']
})
export class Tire2ShowComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;
  public myFormData: any = {};

  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private ticketService: TicketService,
    private toastr : ToastrService,
  ) { }


  ngOnInit(): void {
    this._getFormData();
  }

  _getFormData(){

    const id =  this.route.snapshot.params.id;
    const apiURL = this.baseUrl + '/ticket/tire2/get/' + id;

    const queryParams: any = {};
    queryParams.rEntityName = 'Ticket';
    queryParams.rActiveOperation = 'read';

    this.spinnerService.show();
    this.ticketService.sendGetRequest(apiURL, queryParams)
      .subscribe(
        response => {
          if(response.status === true){
            this.spinnerService.hide();
            this.myFormData = response.data;
            console.log(this.myFormData);
          }else{
            this.spinnerService.hide();
            this.toastr.error(response.message, 'Error');
          }
        },
        error => {
          this.spinnerService.hide();
          console.log('Error: ', error);
        }
      );
}

}

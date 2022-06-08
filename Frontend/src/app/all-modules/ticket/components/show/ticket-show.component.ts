import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { TicketService } from '../../service/ticket.service';

@Component({
  selector: 'app-ticket-show',
  templateUrl: './ticket-show.component.html',
  styleUrls: ['./ticket-show.component.css']
})
export class TicketShowComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;
  public myFormData: any = {};
  public msgListData: any = [];

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
    this._getMessageByCode();
  }

  _getFormData(){

    const id =  this.route.snapshot.params.id;
    const apiURL = this.baseUrl + '/ticket/bbs/get/' + id;

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
  _getMessageByCode(){

    const id =  this.route.snapshot.params.id;
    const apiURL = this.baseUrl + '/message/getByTicketId/' + id;

    const queryParams: any = {};
    queryParams.rEntityName = 'Message';
    queryParams.rActiveOperation = 'read';

    this.spinnerService.show();
    this.ticketService.sendGetRequest(apiURL, queryParams)
      .subscribe(
        response => {
          if(response.status === true){
            this.spinnerService.hide();
            this.msgListData = response.data;
            console.log(this.msgListData);
          }else{
            this.spinnerService.hide();
            //this.toastr.info(response.message, 'info');
          }
        },
        error => {
          this.spinnerService.hide();
          console.log('Error: ', error);
        }
      );

  }

}

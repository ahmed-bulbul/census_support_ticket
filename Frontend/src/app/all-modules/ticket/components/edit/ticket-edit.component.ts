import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { TicketService } from '../../service/ticket.service';

@Component({
  selector: 'app-ticket-edit',
  templateUrl: './ticket-edit.component.html',
  styleUrls: ['./ticket-edit.component.css']
})
export class TicketEditComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;

  public isSubmitted = false;
  public myFormData: any = {};

  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private ticketService:TicketService,
    private toastr:ToastrService
  ) { }

  ngOnInit(): void {
    this._initForm();
    this._getFormData();
  }

  _initForm(){

    this.myForm = this.formBuilder.group({
      id: [''],
      deviceUserPhone: ['', [Validators.required,Validators.minLength(11)]],
      deviceUserId: ['', [Validators.required]],
      tabletSerialNo: ['', [Validators.required]],
      problemCategory: ['', [Validators.required]],
      problemType: [''],
      problemDescription:[''],
      code: [''],
      createdBy:[''],
      status:[''],
      holdTime:[''],
      solutionType:[''],
      solutionDescription:[''],
      solvedBy:[''],
    });

}

_getFormData(){

  const id =  this.route.snapshot.params.id;
  const apiURL = this.baseUrl + '/ticket/bbs/get/' + id;

  const queryParams: any = {};
  queryParams.rEntityName = 'Ticket';
  queryParams.rActiveOperation = 'update';

  this.spinnerService.show();
  this.ticketService.sendGetRequest(apiURL, queryParams).subscribe(
    (response: any) => {
      if(response.status === true){
        const data = response.data;
        console.log(data);
        this.myFormData = data;
        this.spinnerService.hide();

        this.myForm.patchValue(data);
      }else{
        this.spinnerService.hide();
        this.toastr.error(response.message, 'Error');
      }
    },
    (error) => {
      console.log(error)
    }
  );
}

myFormSubmit(){
  const apiURL = this.baseUrl + '/ticket/bbs/update';
    let formData: any = {};
    formData = this.myForm.value
    console.log(formData);
    this.spinnerService.show();
    this.ticketService.sendPutRequest(apiURL,formData).subscribe((response: any) => {
      if(response.status === true){
        this.spinnerService.hide();
        this.toastr.success(response.message, 'Success');
        this.router.navigate(['/ticket/bbs/list']);
      }else{
        this.spinnerService.hide();
        this.toastr.error(response.message, 'Error');
      }
    });
}

get f() { return this.myForm.controls; }

resetFormValues(){

}

}

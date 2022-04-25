import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { TicketService } from '../../service/ticket.service';

@Component({
  selector: 'app-ticket-create',
  templateUrl: './ticket-create.component.html',
  styleUrls: ['./ticket-create.component.css']
})
export class TicketCreateComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;
  public parentMenu: any = [];
  public isSubmitted = false;


  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private toastr:ToastrService,
    private ticketService:TicketService
  ) { }

  ngOnInit(): void {
    this._initForm();
  }

  _initForm(){

      this.myForm = this.formBuilder.group({
        id: [''],
        deviceUserPhone: ['', [Validators.required,Validators.minLength(11)]],
        devicePhone: ['', [Validators.required,Validators.minLength(11)]],
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

  myFormSubmit(){

    const apiURL = this.baseUrl + '/ticket/bbs/create';
    this.isSubmitted = true;
    if(this.myForm.invalid){
      return;
    }

    let formData: any;
    formData = Object.assign(this.myForm.value,{
      createdBy: this._getCreatedUser().value ? {id: this._getCreatedUser().value} : null,
      solvedBy: this._getSolvedByUser().value ? {id: this._getSolvedByUser().value} : null,

    });
    formData.status='OPEN';
    formData.rEntityName = 'Ticket';
    formData.rActiveOperation = 'Create';

    this.spinnerService.show();
    this.ticketService.sendPostRequest(apiURL, formData).subscribe(
      (response: any) => {
        if(response.status === true){
          console.log(response);
          this.spinnerService.hide().then(r => console.log('spinner stopped'));
          this.toastr.success('Ticket created successfully', 'Success', { positionClass:'toast-custom' });
          this.router.navigate(['/ticket/bbs/list'], {relativeTo: this.route}).then(r => console.log('navigated'));
        }else{
          this.spinnerService.hide().then(r => console.log('spinner stopped'));
          this.toastr.error(response.message, 'Error');
        }
      },
      (error) => {
        console.log(error.message);
        this.toastr.show(error.error.message, 'Show');
        this.spinnerService.hide().then(r => console.log('spinner stopped'));
      }
    );


  }

  get f() { return this.myForm.controls; }
  resetFormValues(){
    //reset form
    this.myForm.reset();
  }

  _getCreatedUser(){
    return this.myForm.get('createdBy');
  }
  _getSolvedByUser(){
    return this.myForm.get('solvedBy');
  }

}

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
        deviceUserPhone: ['', [Validators.required,Validators.minLength(12)]],
        deviceUserId: ['', [Validators.required]],
        tabletSerialNo: ['', [Validators.required]],
        problemCategory: ['', [Validators.required]],
        problemType: [''],
        problemDescription:[''],
        code: ['', [Validators.required,Validators.minLength(3)]],
        createdBy:[''],
        status:[''],
        holdTime:[''],
        solutionType:[''],
        solutionDescription:[''],
        solvedBy:[''],
      });

  }

}

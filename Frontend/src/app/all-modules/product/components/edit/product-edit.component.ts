import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { ProductService } from '../../service/product.service';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {


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
    private productService:ProductService,
    private toastr:ToastrService
  ) { }

  ngOnInit(): void {
    this._initForm();
    this._getFormData();
  }

  _initForm(){

  this.myForm = this.formBuilder.group({
    id: [''],
    name:['', [Validators.required]],
    description: [''],
    image: [''],
    price:[''],
    brand:['',[Validators.required]],
    color: [''],
    size: [''],
    type:[''],
  });
}

_getFormData(){

  const id =  this.route.snapshot.params.id;
  const apiURL = this.baseUrl + '/product/get/' + id;

  const queryParams: any = {};
  queryParams.rEntityName = 'Product';
  queryParams.rActiveOperation = 'update';

  this.spinnerService.show();
  this.productService.sendGetRequest(apiURL, queryParams).subscribe(
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

  updateForm(){
    const apiURL = this.baseUrl + '/product/update';
    let formData: any = {};
    formData = this.myForm.value
    console.log(formData);
    this.spinnerService.show();
    this.productService.sendPutRequest(apiURL,formData).subscribe((response: any) => {
      if(response.status === true){
        this.spinnerService.hide();
        this.toastr.success(response.message, 'Success');
        this.router.navigate(['/products/product/list']);
      }else{
        this.spinnerService.hide();
        this.toastr.error(response.message, 'Error');
      }
    });

  }

  resetFormValues(){

  }


  get f() {
    return this.myForm.controls;
  }


}

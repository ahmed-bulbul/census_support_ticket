import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from 'src/app/login/services/login.services';
import { environment } from 'src/environments/environment';
import { ProductService } from '../../service/product.service';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private toastr:ToastrService,
    private loginService:LoginService
  ) { }

  ngOnInit(): void {
    this._initForm();
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
      user:['']
    });

  }

  myFormSubmit(){

    let apiURL = this.baseUrl + "/product/create";

    if(this.myForm.invalid){
      return;
    }

    let formData: any = {};
    formData = Object.assign(this.myForm.value,{
      user: {id: this.loginService.getUser().id}
    });
    formData.rActiveOperation = "Create";

    console.log(formData);

    this.spinnerService.show();
    this.productService.sendPostRequest(apiURL, formData).subscribe(
      (response: any) => {
        if(response.status == true){
          console.log(response);
          this.spinnerService.hide();
          this.router.navigate(["/products/product/list"], {relativeTo: this.route});
        }else{
          this.spinnerService.hide();
          this.toastr.error(response.message, 'Error');
        }

      },
      (error) => {
        console.log(error);
        this.spinnerService.hide();
      }
    );
  }

  resetFormValues(){
    this.myForm.reset();
  }


  get f() { return this.myForm.controls; }

}

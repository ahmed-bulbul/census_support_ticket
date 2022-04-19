import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { ProductService } from '../../service/product.service';

@Component({
  selector: 'app-product-show',
  templateUrl: './product-show.component.html',
  styleUrls: ['./product-show.component.css']
})
export class ProductShowComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;
  public myFormData: any = {};

  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private productService: ProductService,
    private toastr : ToastrService,
  ) { }

  ngOnInit(): void {
    this._getFormData();
  }

  _getFormData(){

    let id =  this.route.snapshot.params.id;
    let apiURL = this.baseUrl + "/product/get/" + id;

    let queryParams: any = {};
    queryParams.rEntityName = "Product";
    queryParams.rActiveOpetation = "read";

    this.spinnerService.show();
    this.productService.sendGetRequest(apiURL, queryParams).subscribe(
      (response: any) => {
        if(response.status == true){
          this.myFormData = response.data;
          this.spinnerService.hide();
          this.myForm.patchValue(this.myFormData);
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

  saveUpdatedFormData(){
    
  }


}

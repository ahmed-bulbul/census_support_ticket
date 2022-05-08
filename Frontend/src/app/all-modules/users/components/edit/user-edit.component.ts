import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  public baseUrl = environment.baseUrl;
  public myForm: FormGroup;

  public rolesData: any = [];
  public selectedRoles : any =[];
  public isSubmitted = false;

  public myFormData: any = {};

  constructor(
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: NgxSpinnerService,
    private toastr:ToastrService,
    private userService:UserService,
  ) { }

  ngOnInit(): void {
    this._initForm();
    this._initButtonsRippleEffect();
    this._getRoles();
    this._getFormData();
  }


  _initForm(){
    this.myForm = this.formBuilder.group({
      id: [''],
      username: ['', [Validators.required,Validators.minLength(3)]],
      // email: ['', [Validators.required,Validators.email]],
      phone: ['', [Validators.required,Validators.minLength(11), Validators.pattern('[0-9]+')]],
      // password: ['', [Validators.required,Validators.minLength(4)]],
      // confirmPassword: ['', Validators.required],
      role:[],
    });
  }

  checkRole( event,role){
    if(event.target.checked){
      this.selectedRoles.push(role);
    }else if(event.target.checked === false){
      this.selectedRoles.splice(this.selectedRoles.indexOf(role),1);
    }else{
      const index = this.rolesData.indexOf(role);
      this.selectedRoles.splice(index,1);
    }
     console.log(this.selectedRoles);

  }

  _getRoles(){
    const apiURL = this.baseUrl + '/acl/roles';
    const queryParams: any = {};
    this.userService.sendGetRequest(apiURL,queryParams).subscribe((response: any) => {
      if(response.status === true){
        this.rolesData = response.data;
      }else{
        this.toastr.error(response.message, 'Error');
      }
    });
  }

  setFormDefaultValues(){
    //
  }

  resetFormValues(){

    this.myForm.reset();
    this.setFormDefaultValues();

  }

  _initButtonsRippleEffect(){
    // tslint:disable-next-line:only-arrow-functions
    const createRipple = function(e){

      const button = e.currentTarget;

      const x = e.clientX - e.target.getBoundingClientRect().left;
      const y = e.clientY - e.target.getBoundingClientRect().top;

        // Create span element
        const ripple = document.createElement('span');
        // Position the span element
        ripple.style.cssText = 'position: absolute; background: #fff; transform: translate(-50%, -50%); pointer-events: none; border-radius: 50%; animation: animate 1s linear infinite;';
        ripple.style.left = `${x}px`;
        ripple.style.top = `${y}px`;

        // Add span to the button
        button.appendChild(ripple);

        // Remove span after 0.3s
        setTimeout(() => {
              ripple.remove();
        }, 1000);

    }

    const elements = document.querySelectorAll('.btn-ripple') as any as Array<HTMLElement>
    elements.forEach(element => {
      // tslint:disable-next-line:only-arrow-functions
      element.addEventListener('click', function(e){
        createRipple(e);
      });
    });

  }

  _getFormData(){
    const id =  this.route.snapshot.params.id;
    const apiURL = this.baseUrl + '/acl/user/get/' + id;

    const queryParams: any = {};
    queryParams.rEntityName = 'User';
    queryParams.rActiveOperation = 'update';

    this.spinnerService.show();
    this.userService.sendGetRequest(apiURL, queryParams).subscribe(
      (response: any) => {
        if(response.status === true){
          const data = response.data;
          console.log(data);
          this.myFormData = data;
          this.spinnerService.hide();
          data.confirmPassword = data.password;
          const roles = data.role;
          roles.forEach(element => {
             const role = element.split('-')[1];
             this.selectedRoles.push(role);
             if(role === 'ROLE_SUPER_ADMIN'){
              $('#ROLE_SUPER_ADMIN_ID').prop('checked', true);
            }
            if(role === 'ROLE_ADMIN'){
               $('#ROLE_ADMIN_ID').prop('checked', true);
            }
            if(role === 'ROLE_USER'){
               $('#ROLE_USER_ID').prop('checked', true);
            }
            if(role === 'ROLE_BBS_USER'){
              $('#ROLE_BBS_USER').prop('checked', true);
            }

            if(role === 'ROLE_TIRE1_USER'){
              $('#ROLE_TIRE1_USER').prop('checked', true);
            }

            if(role === 'ROLE_TIRE2_USER'){
              $('#ROLE_TIRE2_USER').prop('checked', true);
            }


          });

          console.log(roles);
          data.role =  this.selectedRoles,
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
    const apiURL = this.baseUrl + '/acl/user/update';
    let formData: any = {};
    formData = this.myForm.value
    console.log(formData);
    this.spinnerService.show();
    this.userService.sendPutRequest(apiURL,formData).subscribe((response: any) => {
      if(response.status === true){
        this.spinnerService.hide();
        this.toastr.success(response.message, 'Success');
        this.router.navigate(['/users/user/list']);
      }else{
        this.spinnerService.hide();
        this.toastr.error(response.message, 'Error');
      }
    });

  }
  get f() {
    return this.myForm.controls;
  }


}

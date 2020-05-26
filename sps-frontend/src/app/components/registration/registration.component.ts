import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../service/auth.service';
import {FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {UserDto} from '../../domain/dto/user-dto.model';
import {HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  get email() { return this.registrationForm.get('email'); }
  get username() { return this.registrationForm.get('username'); }
  get firstName() { return this.registrationForm.get('firstName'); }
  get lastName() { return this.registrationForm.get('lastName'); }
  get phoneNumber() { return this.registrationForm.get('phoneNumber'); }
  get password() { return this.registrationForm.get('password'); }
  get repeatPassword() { return this.registrationForm.get('repeatPassword'); }

  registrationForm: FormGroup;

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,
              private router: Router) { }

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      username: ['', [Validators.required, Validators.maxLength(20)]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      phoneNumber: ['', [Validators.required, Validators.maxLength(8)]],
      password: ['', Validators.required],
      repeatPassword: ['', Validators.required]
    }, {validators: passwordMatchValidator});
  }

  onSubmit() {
    const userDto: UserDto = {
      email: this.email.value,
      username: this.username.value,
      firstName: this.firstName.value,
      lastName: this.lastName.value,
      phoneNumber: this.phoneNumber.value,
      password: this.password.value
    };

    this.authService.singUp(userDto).toPromise()
      .then(() => this.router.navigateByUrl('/login'))
      .catch((error: HttpErrorResponse) => {
        console.error(error);
        this.handleError(error);
      });
  }

  private handleError(error: HttpErrorResponse) {
    console.log(error.error.inputField);
    const inputField: string = error.error.inputField;
    this.registrationForm.get(inputField).setErrors({taken: true});
  }

}

export const passwordMatchValidator: ValidatorFn = (formGroup: FormGroup): ValidationErrors | null => {
  if (formGroup.get('password').value === formGroup.get('repeatPassword').value) {
    return null;
  }
  formGroup.get('repeatPassword').setErrors({mismatch: true});
  console.log(formGroup.get('repeatPassword').getError('mismatch'));
  return {mismatch: true};
};

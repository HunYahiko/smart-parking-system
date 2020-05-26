import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../service/auth.service';
import {LoginDto} from '../../domain/dto/login-dto.model';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse} from '@angular/common/http';
import {HttpStatus} from '../../enums/HttpStatus';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,
              private router: Router) { }

  get username() { return this.singInForm.get('username'); }

  get password() { return this.singInForm.get('password'); }

  get rememberMe() { return this.singInForm.get('rememberMe'); }

  singInForm: FormGroup;

  ngOnInit(): void {
    this.singInForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      rememberMe: [false]
    });
  }

  onSubmit() {
    const loginDto: LoginDto = {
      username: this.username.value,
      password: this.password.value
    };

    this.authService.singIn(loginDto).toPromise()
      .then(tokenDto => {
        this.updateStore(tokenDto.token);
        this.router.navigateByUrl('/dashboard')
          .then(value => console.log('moved to dashboard'))
          .catch(reason => console.error('Could not navigate to dashboard:' + reason));
      })
      .catch(error => {
        this.handleError(error);
      });
  }

  navigateToRegistration() {
    this.router.navigateByUrl('/register')
      .then(value => console.log('moved to registration'))
      .catch(reason => console.error('Could not navigate to registration: ' + reason));
  }

  private updateStore(token: string) {
    if (this.rememberMe.value) {
      localStorage.setItem(this.authService.TOKEN_KEY, token);
      return;
    }
    sessionStorage.setItem(this.authService.TOKEN_KEY, token);
  }

  private handleError(httpError: HttpErrorResponse) {
    if (httpError.status === HttpStatus.NOT_FOUND) {
      this.username.setErrors({ invalid: true });
      this.password.setErrors({ invalid: true });
    }

    if (httpError.status === HttpStatus.INTERNAL_SERVER_ERROR) {
      console.error('Something went wrong!');
    }

    if (httpError.status === HttpStatus.ERR_CONNECTION_REFUSED) {
      console.error('Servers might be down');
    }
  }

}

import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { LoginComponent } from './login.component';
import {DebugElement} from '@angular/core';
import {By} from '@angular/platform-browser';
import {HttpClient, HttpClientModule, HttpErrorResponse, HttpHandler, HttpXhrBackend} from '@angular/common/http';
import {AuthService} from '../../service/auth.service';
import {FormBuilder} from '@angular/forms';
import {Router} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {TokenResponseDto} from '../../domain/dto/token-response-dto.model';
import {LoginDto} from '../../domain/dto/login-dto.model';
import {Observable} from 'rxjs';
import {HttpStatus} from '../../enums/HttpStatus';


describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let debugElement: DebugElement;
  let element: HTMLElement;

  let httpClientSpy: { get: jasmine.Spy, post: jasmine.Spy, singIn: jasmine.Spy };
  let authService: AuthService;

  beforeEach(async(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get', 'post']);
    authService = new AuthService(httpClientSpy as any);
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      declarations: [ LoginComponent ],
      providers: [
        { provide: AuthService, useValue: authService },
        { provide: FormBuilder, useClass: FormBuilder }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    debugElement = fixture.debugElement.query(By.css('form'));
    element = debugElement.nativeElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it ('submit button should be disabled at empty form', async(() => {
    debugElement = fixture.debugElement.query(By.css('button'));
    expect(debugElement.nativeElement.disabled).toBe(true);
  }));

  it ('expect sign in form to be invalid when form is empty', async(() => {
    expect(component.singInForm.valid).toBe(false);
  }));

  it ('expect sign in form to be valid when form is completed', async(() => {
    component.username.setValue('mock');
    component.password.setValue('mock');
    fixture.detectChanges();
    expect(component.singInForm.valid).toBe(true);
  }));

  it ('should set a JWT token in session storage on valid input', fakeAsync(() => {
    const expectedResponse: TokenResponseDto = {
      token: 'mock_token'
    };

    component.username.setValue('mock');
    component.password.setValue('mock');
    component.rememberMe.setValue(false);

    executeMockSingInRequest(expectedResponse);

    expect(sessionStorage.getItem(authService.TOKEN_KEY)).toBe(expectedResponse.token);
    expect(localStorage.getItem(authService.TOKEN_KEY)).toBeNull();
  }));

  it ('should set a JWT token in local storage on valid input', fakeAsync(() => {
    const expectedResponse: TokenResponseDto = {
      token: 'mock_token'
    };

    component.username.setValue('mock');
    component.password.setValue('mock');
    component.rememberMe.setValue(true);

    executeMockSingInRequest(expectedResponse);

    expect(localStorage.getItem(authService.TOKEN_KEY)).toBe(expectedResponse.token);
    expect(sessionStorage.getItem(authService.TOKEN_KEY)).toBeNull();
  }));

  it ('should set invalid errors on form inputs ', fakeAsync(() => {
    const errorResponse: HttpErrorResponse = new HttpErrorResponse({
      error: 'test 404 error',
      status: HttpStatus.NOT_FOUND,
      statusText: 'Not Found'
    });

    component.username.setValue('mock');
    component.password.setValue('mock');
    fixture.detectChanges();

    expect(component.singInForm.valid).toBe(true);

    httpClientSpy.post.and.returnValue(new Observable<TokenResponseDto>(observer => {
      observer.error(errorResponse);
      observer.complete();
    }));

    component.onSubmit();
    tick(1000);

    expect(component.username.hasError('invalid')).toBe(true);
    expect(component.password.hasError('invalid')).toBe(true);
  }));

  const executeMockSingInRequest = (expectedResponse: TokenResponseDto) => {
    localStorage.clear();
    sessionStorage.clear();
    fixture.detectChanges();
    expect(component.singInForm.valid).toBe(true);
    httpClientSpy.post.and.returnValue(new Observable<TokenResponseDto>(observer => {
      observer.next(expectedResponse);
      observer.complete();
    }));

    component.onSubmit();
    tick(1000);
  };
});

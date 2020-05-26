import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginDto} from '../domain/dto/login-dto.model';
import {Observable} from 'rxjs';
import {TokenResponseDto} from '../domain/dto/token-response-dto.model';
import {UserDto} from '../domain/dto/user-dto.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly BASE_URL: string = 'http://192.168.0.54:8080';
  private readonly SING_IN_URL: string = '/v1/api/auth/sing-in';
  private readonly SING_UP_URL: string = '/v1/api/users';
  public readonly TOKEN_KEY: string = 'jwtToken';

  constructor(private httpClient: HttpClient) { }

  public getToken(): string {
    let token: string = sessionStorage.getItem(this.TOKEN_KEY);
    if (token == null) {
      token = localStorage.getItem(this.TOKEN_KEY);
    }
    return token;
  }

  public singIn(loginDto: LoginDto): Observable<TokenResponseDto> {
    return this.httpClient.post<TokenResponseDto>(this.BASE_URL + this.SING_IN_URL, loginDto);
  }

  public singUp(userDto: UserDto): Observable<any> {
    return this.httpClient.post(this.BASE_URL + this.SING_UP_URL, userDto);
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  public sendGetRequest(apiURL, queryParams){
    console.log("@sendGetRequest");
    return this.http.get<any>(apiURL, {params: queryParams}).pipe( retry(3));
  }

  public sendPostRequest(apiURL, formData){
    console.log("@sendPostRequest");
    return this.http.post(apiURL, formData);
  }

  public sendPutRequest(apiURL, formData){
    console.log("@sendPutRequest");
    return this.http.put(apiURL, formData);
  }

  public sendDeleteRequest(apiURL, formData){
    console.log("@sendDeleteRequest");
    return this.http.delete(apiURL, formData);

  }


  // getUsers(): Observable<User[]> {
  //   return this.http.get<User[]>(this.apiURLUsers);
  // }

  // getUser(userId: string): Observable<User> {
  //   return this.http.get<User>(`${this.apiURLUsers}/${userId}`);
  // }

  // createUser(user: User): Observable<User> {
  //   return this.http.post<User>(this.apiURLUsers, user);
  // }

  // updateUser(user: User): Observable<User> {
  //   return this.http.put<User>(`${this.apiURLUsers}/${user.id}`, user);
  // }

  // deleteUser(userId: string): Observable<any> {
  //   return this.http.delete<any>(`${this.apiURLUsers}/${userId}`);
  // }


}

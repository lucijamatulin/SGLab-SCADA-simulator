import { HttpHeaders } from '@angular/common/http';

export const encodeBasicAuthString = (username: string, password: string) => {
  return btoa(`${username}:${password}`);
};

export const getHeaders = (): HttpHeaders => {
  const headers: HttpHeaders = new HttpHeaders();

  headers.set('ContentType', 'application/json')

  return headers;
};

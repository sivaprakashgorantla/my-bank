import { Injectable } from '@angular/core';
import { HttpEvent, HttpRequest, HttpHandler, HttpInterceptorFn, HttpContext } from '@angular/common/http';
import { AuthService } from './auth.service'; // Adjust the path to your AuthService
import { inject } from '@angular/core';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService); // Inject AuthService using Angular's inject function

  const token = authService.getToken(); // Retrieve the JWT token from AuthService
  console.log('Token in interceptor:', token); // Log the token
  if (token) {
    // Clone the request and add the Authorization header
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    console.log('Cloned Request:', clonedRequest); // Log the cloned request
    return next(clonedRequest); // Continue with the modified request
  }

  return next(req); // If no token, continue with the original request
};

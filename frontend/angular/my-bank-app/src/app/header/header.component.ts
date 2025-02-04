import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  username: string | null = '';
  constructor(private router: Router) {}
  ngOnInit(): void {
    this.username = localStorage.getItem('username');
    console.log('Username in header :', this.username);
  }
  logout(): void {
    // Clear localStorage/sessionStorage or perform a logout API call
    localStorage.clear();
    this.router.navigate(['/login']);
  }

}

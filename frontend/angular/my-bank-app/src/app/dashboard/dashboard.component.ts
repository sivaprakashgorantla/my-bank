import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule, // Add this for ngIf, ngFor, etc.
    RouterModule, // For navigation
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  showProfileMenu: boolean = false;
  username: string | null = '';

  constructor(private router: Router) {}

  ngOnInit(): void {
    // Fetch the logged-in user's username (from localStorage or AuthService)
    this.username = localStorage.getItem('username') || 'Guest';
  }

  toggleProfileMenu(): void {
    this.showProfileMenu = !this.showProfileMenu;
  }

  navigateToProfile(): void {
    this.router.navigate(['/profile']);
  }

  navigateToEditProfile(): void {
    this.router.navigate(['/edit-profile']);
  }
}

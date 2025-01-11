import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ForgetpasswordComponent } from './forgetpassword/forgetpassword.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ViewProfileComponent } from './view-profile/view-profile.component';
import { UpdateProfileComponent } from './update-profile/update-profile.component';
import { LayoutComponent } from './layout/layout.component'; // Ensure this path is correct and the file exists

export const routes: Routes = [
  {
    path: '', component: LayoutComponent, children: [
      { path: '', component: DashboardComponent },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'view-profile', component: ViewProfileComponent },
      { path: 'update-profile', component: UpdateProfileComponent }
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forgot-password', component: ForgetpasswordComponent },

  { path: '', redirectTo: '/login', pathMatch: 'full' },
];


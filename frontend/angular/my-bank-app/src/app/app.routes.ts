import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ForgetpasswordComponent } from './forgetpassword/forgetpassword.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ViewProfileComponent } from './view-profile/view-profile.component';
import { UpdateProfileComponent } from './update-profile/update-profile.component';
import { LayoutComponent } from './layout/layout.component'; // Ensure this path is correct and the file exists
import { AccountDetailsComponent } from './account-details/account-details.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { TransactionHistoryComponent } from './transaction-history/transaction-history.component';
import { BeneficiaryListComponent } from './beneficiary-list/beneficiary-list.component';
import { BeneficiaryComponent } from './beneficiary/beneficiary.component';
import { AddBeneficiaryComponent } from './beneficiary/add-beneficiary/add-beneficiary.component';

export const routes: Routes = [
  {
    path: '', component: LayoutComponent, children: [
      { path: '', component: DashboardComponent },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'view-profile', component: ViewProfileComponent },
      { path: 'update-profile', component: UpdateProfileComponent },
      { path: 'app-account-details/:accountNumber', component: AccountDetailsComponent },
      { path: 'app-transaction-history/:accountNumber', component: TransactionHistoryComponent },
      { path: 'app-transaction/:accountNumber', component: TransactionsComponent },
      { path: 'app-beneficiary-list', component: BeneficiaryListComponent },
      { path: 'app-beneficiary', component: BeneficiaryComponent },
      { path: 'app-add-beneficiary', component: AddBeneficiaryComponent },
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forgot-password', component: ForgetpasswordComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];


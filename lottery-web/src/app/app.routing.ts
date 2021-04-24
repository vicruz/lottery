import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule  } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';


import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './core/auth.guard';
import { SignupComponent } from './signup/signup.component';

const routes: Routes =[
  {
    path: '',
    //redirectTo: 'dashboard',
    redirectTo: 'raffles',
    pathMatch: 'full',
  }, 
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  {
    path: '',
    component: AdminLayoutComponent,
    children: [{
      path: '',
      loadChildren: './layouts/admin-layout/admin-layout.module#AdminLayoutModule'
    }],
    canActivate: [AuthGuard] 
  }
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes,{
      relativeLinkResolution: 'legacy'
    })
  ],
  exports: [RouterModule],
})
export class AppRoutingModule { }

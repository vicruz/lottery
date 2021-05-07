import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'app/_services/token-storage.service';

declare const $: any;
declare interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
}
export const ROUTES: RouteInfo[] = [
    { path: '/raffles', title: 'Sorteos',  icon:'casino', class: '' },
    { path: '/users-panel', title: 'Usuarios',  icon:'person', class: '' }
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  menuItems: any[];
  isAdmin = false;
  usersPath =  "/users-panel";
  rafflePath = "/raffles";

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    const user = this.tokenStorageService.getUser();
      let roles = user.roles;
      this.isAdmin = roles.includes('ROLE_ADMIN');
      
    this.menuItems = ROUTES.filter(menuItem => menuItem);
  }
  isMobileMenu() {
      if ($(window).width() > 991) {
          return false;
      }
      return true;
  };

  public logout(){
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}

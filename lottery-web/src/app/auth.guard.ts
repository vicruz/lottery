import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenStorageService } from './_services/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private tokenStorageService: TokenStorageService, public router: Router){}
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      const rolesAdmited = route.data.roles;
      const user = this.tokenStorageService.getUser();
      let roles = user.roles;

      if(rolesAdmited == null || rolesAdmited.length == 0) return true;

      for(let idx=0; idx<roles.length; idx++){
        for(let jdx=0; jdx<rolesAdmited.length; jdx++){
          if(roles[idx].includes(rolesAdmited[jdx])){
            return true;
          }
        }
      }

      this.router.navigate(['raffles']);
    return false;
  }
  
}

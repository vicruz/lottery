import { Component, OnInit } from '@angular/core';
import { UserInfo } from 'app/response/user-info';
import { UserService } from 'app/_services/user.service';

declare var $: any;

@Component({
  selector: 'app-users-panel',
  templateUrl: './users-panel.component.html',
  styleUrls: ['./users-panel.component.css']
})
export class UsersPanelComponent implements OnInit {

  data: UserInfo[];

  constructor(private userService:UserService) { }

  ngOnInit(): void {

    this.userService.getAllUsers().subscribe((resp:UserInfo[])=>{
      resp.forEach(user=>{
        user.adminRole = false;
        for(let idx=0; idx<user.roles.length; idx++){
          if(user.roles[idx]==='ROLE_ADMIN'){
            user.adminRole = true;
          }
        }
      });
      this.data = resp;
    });
  }

  onChangeStatus(user:UserInfo){
    this.userService.updateRole(user.id, !user.adminRole).subscribe(resp =>{
      this.notification('bottom','right','notifications','Rol actualizado para el usuario ' + user.displayName,'success');
    })
  }

  notification(from, align, iconMessage, messageShow, typeMessage){
    $.notify({
        icon: iconMessage,
        message: messageShow
    },{
        type: typeMessage,
        timer: 4000,
        placement: {
            from: from,
            align: align
        },
        template: '<div data-notify="container" class="col-xl-4 col-lg-4 col-11 col-sm-4 col-md-4 alert alert-{0} alert-with-icon" role="alert">' +
          '<button mat-button  type="button" aria-hidden="true" class="close mat-button" data-notify="dismiss">  <i class="material-icons">close</i></button>' +
          '<i class="material-icons" data-notify="icon">notifications</i> ' +
          '<span data-notify="title">{1}</span> ' +
          '<span data-notify="message">{2}</span>' +
          '<div class="progress" data-notify="progressbar">' +
            '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
          '</div>' +
          '<a href="{3}" target="{4}" data-notify="url"></a>' +
        '</div>'
    });
  }

}

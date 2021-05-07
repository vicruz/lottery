import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AssignNumberModalComponent } from 'app/assign-number-modal/assign-number-modal.component';
import { AppConstants } from 'app/common/app.constants';
import { NotificationsComponent } from 'app/notifications/notifications.component';
import { RaffleService } from 'app/_services/raffle.service';

@Component({
  selector: 'app-assign-winner-modal',
  templateUrl: './assign-winner-modal.component.html',
  styleUrls: ['./assign-winner-modal.component.css']
})
export class AssignWinnerModalComponent implements OnInit {

  raffleForm: FormGroup;
  raffleId:number;
  isSubmited = false;
  notificationComponent:NotificationsComponent;

  constructor(private dialogRef: MatDialogRef<AssignNumberModalComponent>, private formBuilder: FormBuilder,
    private raffleService:RaffleService, @Inject(MAT_DIALOG_DATA) public data) { 
    this.raffleId = data.raffleId;
    
    this.notificationComponent = new NotificationsComponent();
    this.raffleForm = this.formBuilder.group({
      formNumber: ['',Validators.required]
    });
  }

  ngOnInit(): void {
  }

  public onSubmit(){
    this.isSubmited = true;
    if(this.raffleForm.invalid){
      return;
    }

    const raffleNumber = this.raffleForm.controls.formNumber.value;

    this.raffleService.updateStatus(this.raffleId, raffleNumber).subscribe(resp=>{
      this.notificationComponent.showNotificationSuccess("Se ha actualizado el sorteo");
      close();
    }, err=>{
      this.notificationComponent.showNotificationDanger("Ocurrió un error al actualizar el sorteo");
    });
  }

  public close(){
    this.dialogRef.close();
  }

}

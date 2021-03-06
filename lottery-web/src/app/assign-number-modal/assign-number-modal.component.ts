import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NotificationsComponent } from 'app/notifications/notifications.component';
import { RaffleNumbers } from 'app/response/raffle-numbers';
import { RaffleService } from 'app/_services/raffle.service';
import { TokenStorageService } from 'app/_services/token-storage.service';

@Component({
  selector: 'app-assign-number-modal',
  templateUrl: './assign-number-modal.component.html',
  styleUrls: ['./assign-number-modal.component.css']
})
export class AssignNumberModalComponent implements OnInit {

  raffleName:string;
  raffleId:number;
  availableNumbers:RaffleNumbers[];
  numberAssigned:RaffleNumbers;
  notificationComponent:NotificationsComponent;
  numberForm: FormGroup;
  amount:number;
  isSubmited = false;
  buttonAssigned = false;

  constructor(private dialogRef: MatDialogRef<AssignNumberModalComponent>, private raffleService:RaffleService, 
    private formBuilder: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data) {
    console.log(data.raffleId);
    this.raffleName = data.raffleName;
    this.raffleId = data.raffleId;

    this.availableNumbers = [];
    this.numberAssigned = new RaffleNumbers();
    this.numberAssigned.number = -1;
    this.notificationComponent = new NotificationsComponent();

    this.numberForm = this.formBuilder.group({
      numberSelected: ['', Validators.required]
    });
   }


  ngOnInit(): void {
    this.raffleService.getByStatus(this.raffleId, 'Libre').subscribe((resp:RaffleNumbers[])=>{
      this.availableNumbers = resp;
    });
  }

  public blurNumber($event:any){
    let value = $event.target.value;
    this.amount = 0;
    for(let idx=0; idx<this.availableNumbers.length; idx++){
      if(this.availableNumbers[idx].number==value){
        this.amount = this.availableNumbers[idx].amount;
        this.numberAssigned = this.availableNumbers[idx];
        break;
      }
    }
  }

  public close(){
    this.dialogRef.close();
  }

/*
  public getNumber(){
    let idx = 0;
    if(this.availableNumbers.length > 1){
      idx = Math.floor(Math.random()*(this.availableNumbers.length));
    }
    
    this.numberAssigned = this.availableNumbers[idx];
    let currentUser = this.tokenStorage.getUser();
    console.log(currentUser);

    this.raffleService.assign(this.numberAssigned).subscribe(resp=>{ 
      this.notificationComponent.showNotificationSuccess('Numero asignado');
    },
      err=>{
        this.notificationComponent.showNotificationDanger('Ocurrio un error al asignar el numero. Vuelva a intentarlo');
        console.log("valio madres " + err);
      });
  }
*/

  public onSubmit(){
    this.isSubmited = true;
    if(this.numberForm.invalid){
      return;
    }
    
    this.raffleService.assign(this.numberAssigned).subscribe(resp=>{ 
      this.buttonAssigned = true;
      this.notificationComponent.showNotificationSuccess('Numero asignado');
    },
      err=>{
        this.notificationComponent.showNotificationDanger('Ocurrio un error al asignar el numero. Vuelva a intentarlo');
        console.log("valio madres " + err);
      });
  }

}

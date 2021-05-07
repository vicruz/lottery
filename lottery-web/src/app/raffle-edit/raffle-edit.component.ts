import { ChangeDetectorRef, ViewChild } from '@angular/core';
import { ElementRef } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { AssignWinnerModalComponent } from 'app/assign-winner-modal/assign-winner-modal.component';
import { AppConstants } from 'app/common/app.constants';
import { NotificationsComponent } from 'app/notifications/notifications.component';
import { RaffleNumbers } from 'app/response/raffle-numbers';
import { RaffleValues } from 'app/response/raffle-values';
import { FileHandle } from 'app/_directives/imagednd.directive';
import { RaffleService } from 'app/_services/raffle.service';
import { libraryagent } from 'googleapis/build/src/apis/libraryagent';

@Component({
  selector: 'app-raffle-edit',
  templateUrl: './raffle-edit.component.html',
  styleUrls: ['./raffle-edit.component.css']
})
export class RaffleEditComponent implements OnInit {

  minDate: Date;
  collection: RaffleNumbers[];
  //files: any[] = [];
  files: FileHandle[] = [];
  raffleForm: FormGroup;
  //raffleForm: any = {};
  isSubmited = false;
  notificationComponent:NotificationsComponent;
  statusArr:string[] = [AppConstants.FREE, AppConstants.SELECTED, AppConstants.SELLED];
  raffleCode:number = 0;
  layoutFree:number = 0;
  layoutTemp:number = 0;
  layoutPaid:number = 0;
  valueDb:RaffleValues;
  imageShow:any;
  status = AppConstants.ACTIVE;

  
  @ViewChild('description', {static: true}) descriptionElement: ElementRef;
  myDescriptionElement: string = "";
  constructor(private formBuilder: FormBuilder, descriptionElement: ElementRef,
    private raffleService:RaffleService, private activatedRoute: ActivatedRoute,
    private router: Router, private dialog: MatDialog) { 
    this.descriptionElement = descriptionElement;
    this.notificationComponent = new NotificationsComponent();

    this.raffleForm = this.formBuilder.group({
      formName: ['', Validators.required],
      formDate: ['', Validators.required],
      formPercentage: ['',Validators.required]//,
//      formStatus:[]
    });
  }

  ngOnInit(): void {
    const currentYear = new Date().getFullYear();
    const currentMonth = new Date().getMonth();
    const currentDay = new Date().getDate();
    this.minDate = new Date(currentYear, currentMonth, currentDay);
    this.collection = [];
    this.createNumbers();

    this.activatedRoute.paramMap.subscribe(params => {
      this.raffleCode = Number(params.get("id")==null?0:params.get("id"));
      if(this.raffleCode > 0){
        this.raffleService.getOneComplete(this.raffleCode).subscribe((resp:RaffleValues)=>{
          this.valueDb = resp;
          this.raffleForm.controls.formName.setValue(this.valueDb.name);
          this.raffleForm.controls.formDate.setValue(this.valueDb.date);
          this.raffleForm.controls.formPercentage.setValue(this.valueDb.percentage);
  
          this.descriptionElement.nativeElement.value = this.valueDb.description;
  
          this.layoutFree = this.valueDb.freePercentage;
          this.layoutTemp = this.valueDb.selectedPercentage;
          this.layoutPaid = this.valueDb.selledPercentage;

          for(let idx=0; idx<resp.raffleNumbers.length; idx++){
            this.collection[idx].amount = resp.raffleNumbers[idx].amount;
            this.collection[idx].status = resp.raffleNumbers[idx].status;
            this.collection[idx].email = resp.raffleNumbers[idx].email;
            this.collection[idx].oldEmail = resp.raffleNumbers[idx].email;
          }

          if(resp.image != null){
            this.imageShow = 'data:image/jpeg;base64,' + resp.image;
          }

          this.status = resp.status;

          this.collection.slice();
        });
      }
    });

    //this.loadValues();
  }




  filesDropped(files: FileHandle[]): void {
    if(files.length>0 && files[0].file.type.includes("image")){
      if(files[0].file.size > 100000){
        this.notificationComponent.showNotificationWarning('El tamaño máximo de la imagen es de 100k');
        this.files = [];
        return;
      }
      this.files.push(files[0]);
    }else{
      this.notificationComponent.showNotificationWarning('Solo se permiten imágenes');
      this.files = [];
    }
  }

  private createNumbers(){
    for(let idx = 0; idx<100; idx++){
      let raffle: RaffleNumbers = new RaffleNumbers();
      raffle.number = idx;
      raffle.amount = 20;
      raffle.status = AppConstants.FREE;
      this.collection.push(raffle);
    }
  }

  public onSubmit(){
    this.isSubmited = true;
    if(this.raffleForm.invalid){
      return;
    }

    if(this.files.length == 0){
      this.notificationComponent.showNotificationDanger('Se requiere una imagen para crear el sorteo');
      return;
    }

    let info = new RaffleValues();
    info.id = this.raffleCode;
    info.name = this.raffleForm.controls.formName.value;
    info.date = this.raffleForm.controls.formDate.value;
    info.percentage = this.raffleForm.controls.formPercentage.value;
    info.raffleNumbers = this.collection;
    info.description = this.descriptionElement.nativeElement.value;
    info.status = this.status;
    
    let formData = new FormData();
    formData.append('image', this.files[0].file, this.files[0].file.name);
    formData.append('dto', JSON.stringify(info));

    if(info.id > 0){
      this.raffleService.update(info).subscribe((resp:RaffleValues)=>{
        this.raffleService.updateImage(info.id, formData).subscribe(resp=>{
          this.router.navigate(["raffles"]);
        },err=>{
          console.log(err);
          this.notificationComponent.showNotificationDanger('La imagen no pudo ser actualizada');  
        });
        this.notificationComponent.showNotificationSuccess('Sorteo actualizado');
      });
    }else{
      this.raffleService.save(formData).subscribe((resp:RaffleValues)=>{
        this.notificationComponent.showNotificationSuccess('Sorteo almacenado');
        this.router.navigate(["raffles"]);
      });
    }
    

  }

  public changeAmount($event:any, idx:number){
    this.collection[idx].amount = $event.target.value;
  }

  public changeStatus($event:any, idx:number){
    //store the value in a tmp attribute
    if($event.value == 'Libre'){
      this.collection[idx].oldEmail = this.collection[idx].email;
      this.collection[idx].email = '';
    }else{
      this.collection[idx].email = this.collection[idx].oldEmail;
    }

  }

  public assignWinner(raff:RaffleValues){
    this.dialog.open(AssignWinnerModalComponent, { disableClose: true, data:{
      raffleId: this.raffleCode 
    }});
  }

}

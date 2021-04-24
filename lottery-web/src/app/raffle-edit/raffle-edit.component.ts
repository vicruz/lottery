import { ViewChild } from '@angular/core';
import { ElementRef } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NotificationsComponent } from 'app/notifications/notifications.component';
import { RaffleNumbers } from 'app/response/raffle-numbers';
import { RaffleValues } from 'app/response/raffle-values';
import { FileHandle } from 'app/_directives/imagednd.directive';
import { RaffleService } from 'app/_services/raffle.service';

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
  
  @ViewChild('description', {static: true}) descriptionElement: ElementRef;
  myDescriptionElement: string = "";
  constructor(private formBuilder: FormBuilder, descriptionElement: ElementRef,
    private raffleService:RaffleService) { 
    this.descriptionElement = descriptionElement;
    this.notificationComponent = new NotificationsComponent();
  }

  ngOnInit(): void {
    const currentYear = new Date().getFullYear();
    const currentMonth = new Date().getMonth();
    const currentDay = new Date().getDate();
    this.minDate = new Date(currentYear, currentMonth, currentDay);
    this.collection = [];
    this.createNumbers();

    this.raffleForm = this.formBuilder.group({
      formName: ['', Validators.required],
      formDate: ['', Validators.required],
      formPercentage: ['',Validators.required]
    });
  }

  filesDropped(files: FileHandle[]): void {
    if(files.length>0 && files[0].file.type.includes("image")){
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
      raffle.status = 'FREE';
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
    info.name = this.raffleForm.controls.formName.value;
    info.date = this.raffleForm.controls.formDate.value;
    info.percentage = this.raffleForm.controls.formPercentage.value;
    info.raffleNumbers = this.collection;
    info.description = this.descriptionElement.nativeElement.value;
    
    let formData = new FormData();
    formData.append('file', this.files[0].file);
    //info.image = formData;

    this.raffleService.save(info).subscribe((resp:RaffleValues)=>{
      this.notificationComponent.showNotificationSuccess('Sorteo almacenado');
    });

  }

}

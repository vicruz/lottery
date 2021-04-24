import {
  Directive,
  Output,
  Input,
  EventEmitter,
  HostBinding,
  HostListener
} from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

export interface FileHandle {
  file: File,
  url: SafeUrl
}

@Directive({
  selector: '[appImagednd]'
})
export class ImagedndDirective {

  //@HostBinding('class.fileover') fileOver: boolean;
  //@HostBinding("style.background") private background = "#eee";

  //@Output() fileDropped = new EventEmitter<any>();
  //@Output() files: EventEmitter<FileHandle[]> = new EventEmitter();

  constructor(private sanitizer: DomSanitizer) { }

  // Dragover listener
  /*@HostListener('dragover', ['$event']) onDragOver(evt: DragEvent) {
    evt.preventDefault();
    evt.stopPropagation();
    //this.fileOver = true;
    this.background = "#999";
  }

  // Dragleave listener
  @HostListener('dragleave', ['$event']) public onDragLeave(evt: DragEvent) {
    evt.preventDefault();
    evt.stopPropagation();
    //this.fileOver = false;
    this.background = "#eee";
  }

  // Drop listener
  @HostListener('drop', ['$event']) public ondrop(evt: DragEvent) {
    evt.preventDefault();
    evt.stopPropagation();
    this.background = '#eee';

    let files: FileHandle[] = [];
    for (let i = 0; i < evt.dataTransfer.files.length; i++) {
      const file = evt.dataTransfer.files[i];
      const url = this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(file));
      files.push({ file, url });
    }
    if (files.length > 0) {
      this.files.emit(files);
    }

    /*
    this.fileOver = false;
    let files = evt.dataTransfer.files;
    if (files.length > 0) {
      this.fileDropped.emit(files);
    }
    */
//  }

@Output() files: EventEmitter<FileHandle[]> = new EventEmitter();
@HostBinding("style.background") private background = "#eee";

@HostListener("dragover", ["$event"]) public onDragOver(evt: DragEvent) {
  evt.preventDefault();
  evt.stopPropagation();
  this.background = "#999";
}

@HostListener("dragleave", ["$event"]) public onDragLeave(evt: DragEvent) {
  evt.preventDefault();
  evt.stopPropagation();
  this.background = "#eee";
}

@HostListener('drop', ['$event']) public onDrop(evt: DragEvent) {
  evt.preventDefault();
  evt.stopPropagation();
  this.background = '#eee';

  let files: FileHandle[] = [];
  for (let i = 0; i < evt.dataTransfer.files.length; i++) {
    const file = evt.dataTransfer.files[i];
    const url = this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(file));
    files.push({ file, url });
  }
  if (files.length > 0) {
    this.files.emit(files);
  }
}

}

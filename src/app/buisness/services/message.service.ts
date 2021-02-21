import { Injectable, } from '@angular/core';
import { MatSnackBar } from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class MessageService  {
  constructor(private _snackBar: MatSnackBar) {
}
  openSnackBar(message : string,severity :string) {
    this._snackBar.open(message, 'Close', {
      duration: 2000,
      verticalPosition: "top",
      horizontalPosition:"right",
      panelClass: [severity]
    });
  }
}

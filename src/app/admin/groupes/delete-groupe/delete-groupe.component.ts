import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { Groupe } from 'src/app/buisness/models/Groupe';
import { GroupeService } from 'src/app/buisness/services/groupe.service';
import { MessageService } from 'src/app/buisness/services/message.service';
import { SEVERITY_TYPES } from 'src/app/helpers/severities.name';

@Component({
  selector: 'app-delete-groupe',
  templateUrl: './delete-groupe.component.html',
  styleUrls: ['./delete-groupe.component.css']
})
export class DeleteGroupeComponent implements OnInit {
  groupe: Groupe;
  count : number;
  constructor(@Inject(MAT_DIALOG_DATA) data,
    private messageService: MessageService,
    private groupeService : GroupeService) {
    this.groupe = data.groupe;
  }

  ngOnInit() {
    this.groupeService.findUsersOfGroupe(this.groupe.code).subscribe(
      data=>{
        this.count = data;
      }
    )
  }
  onDeleteGroupe(){
    if(this.count !== 0){
      this.messageService.openSnackBar("you can't delete this Groupe ", SEVERITY_TYPES.ERROR_SEV);
  }
  }
}

import { AfterViewInit, Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from 'src/app/auth/authentication.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { PathName } from 'src/app/helpers/path-name';
import { User } from 'src/app/buisness/models/User';
import { Groupe } from 'src/app/buisness/models/Groupe';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { GroupeService } from 'src/app/buisness/services/groupe.service';
import { UserService } from 'src/app/buisness/services/user.service';
import { MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent, MAT_DIALOG_DATA } from '@angular/material';
import { MessageService } from 'src/app/buisness/services/message.service';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { GenderUser } from 'src/app/helpers/Gender-user';
import { SEVERITY_TYPES } from 'src/app/helpers/severities.name';
import { UserTest } from 'src/app/buisness/models/UserTest';
interface Gender {
  code: string;
  icon: string;
}
@Component({
  selector: 'app-edit-user-detail',
  templateUrl: './edit-user-detail.component.html',
  styleUrls: ['./edit-user-detail.component.css']
})
export class EditUserDetailComponent implements OnInit {
  user: User;
  userTest: UserTest;
  url: string;
  active: boolean;
  signUpForm: FormGroup;
  presPass: string;
  genders: Gender[] = [];
  newPass: string;
  confPass: string;
  gendre: Gender;
  groupes: Groupe[];
  filteredGroupes: Observable<Groupe[]>;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  fruitCtrl = new FormControl();
  selectedState: string;
  selectedInputGroupe: Groupe;
  testPass: boolean = false;
  never: boolean = false;
  changePass: boolean = false;
  minDate: Date;
  maxDate: Date

  @ViewChild('groupeInput') groupeInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;
  constructor(private router: Router,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute,
    @Inject(MAT_DIALOG_DATA) data, private messageService: MessageService,
    private groupeService: GroupeService,
    private usersService: UserService,
  ) {
    this.user = data.user;
    const currentYear = new Date().getFullYear();
    this.minDate = new Date(currentYear - 100, 0, 1);
    this.maxDate = new Date(currentYear - 14, 11, 31);
    if (this.user.valid) {
      this.selectedState = "Actif";
    } else {
      this.selectedState = "blocked";
    }
    switch (this.user.sexe) {
      case "MALE": {
        //statements; 
        this.gendre = { code: GenderUser.MALE, icon: "perm_identity" };
        break;
      }
      case "FEMALE": {
        //statements;
        this.gendre = { code: GenderUser.FEMALE, icon: "pregnant_woman" };
        break;
      }
      case "OTHER": {
        //statements; 
        this.gendre = { code: GenderUser.OTHER, icon: "help" };
        break;
      }
      default: {
        //statements; 
        this.gendre = { code: GenderUser.MALE, icon: "perm_identity" };
        break;
      }
      //  this.filteredGroupes=this.groupeService.findDispoGroupes(this.user.username);
    }
    this.groupes = [];
    this.filteredGroupes = this.fruitCtrl.valueChanges.pipe(
      startWith(null),
      map((groupe: string | null) => groupe ? this._filter(groupe) : this.user
        .appGroupeListDispo));
  }
  ngOnInit() {
    this.url = this.user.profilImage;
    this.genders.push({ code: GenderUser.MALE, icon: "perm_identity" });
    this.genders.push({ code: GenderUser.FEMALE, icon: "pregnant_woman" });
    this.genders.push({ code: GenderUser.OTHER, icon: "help" });
    switch (this.user.sexe) {
      case "MALE": {
        //statements; 
        this.gendre = { code: GenderUser.MALE, icon: "perm_identity" };
        break;
      }
      case "FEMALE": {
        //statements;
        this.gendre = { code: GenderUser.FEMALE, icon: "pregnant_woman" };
        break;
      }
      case "OTHER": {
        //statements; 
        this.gendre = { code: GenderUser.OTHER, icon: "help" };
        break;
      }
      default: {
        //statements; 
        this.gendre = { code: GenderUser.MALE, icon: "perm_identity" };
        break;
      }
    }
    this.signUpForm = new FormGroup({
      'userMail': new FormControl(this.user.email, [Validators.required]),
      'selectedRole': new FormControl(null, [Validators.required]),
      'userName': new FormControl(this.user.username, [Validators.required]),
      'image': new FormControl(this.url),
      'signUpRole': new FormControl(null),
      'userfirstName': new FormControl(this.user.firstName, [Validators.required]),
      'userLastName': new FormControl(this.user.lastName, [Validators.required]),
      'userAdresse': new FormControl(this.user.address, [Validators.required]),
      'gender': new FormControl(this.gendre),
      'selectedGender': new FormControl(null),
      'dateNaissance': new FormControl(this.user.dateNaissance, [Validators.required]),
      'userValid': new FormControl(this.selectedState, [Validators.required])
    })
  }
  setGenderFormControl(): Gender {
    switch (this.user.sexe) {
      case "MALE": {
        //statements; 
        let gender = { code: GenderUser.MALE, icon: "perm_identity" };
        return gender;
        break;
      }
      case "FEMALE": {
        //statements;
        let gender = { code: GenderUser.FEMALE, icon: "pregnant_woman" };
        return gender;
        break;
      }
      case "OTHER": {
        //statements; 
        let gender = { code: GenderUser.OTHER, icon: "help" };
        return gender;
        break;
      }
      default: {
        //statements; 
        let gender = { code: GenderUser.MALE, icon: "perm_identity" };
        return gender;
        break;
      }
      //  this.filteredGroupes=this.groupeService.findDispoGroupes(this.user.username);
    }
  }
  getSelectValue() {
    if (this.signUpForm.get('gender') !== undefined) {
      if (this.signUpForm.get('gender').value !== null) {
        let gender = this.signUpForm.get('gender').value;
        this.signUpForm.get('selectedGender').setValue(gender.code);
        return gender.code;
      } else {
        return " ";
      }
    }
  }
  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our fruit
    if ((value || '').trim()) {
      this.groupeService.findByGroupeCode(value).subscribe(data => {
        this.selectedInputGroupe = data;
        this.user.appGroupeList.push(this.selectedInputGroupe);
      })

    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.fruitCtrl.setValue(null);
  }

  remove(fruit: Groupe): void {
    const index = this.user.appGroupeList.indexOf(fruit);
    if (index >= 0) {
      this.user.appGroupeList.splice(index, 1);
      this.user.appGroupeListDispo.push(fruit);
      this.groupes.push(fruit);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    let g = event.option.viewValue;
    this.groupeService.findByGroupeCode(g).subscribe(data => {
      this.selectedInputGroupe = data;
      this.user.appGroupeList.push(this.selectedInputGroupe);
      for (var item of this.user.appGroupeListDispo) {
        let p = this.user.appGroupeListDispo.indexOf(item);
        if (this.user.appGroupeListDispo[p].code === data.code) {
          this.user.appGroupeListDispo.splice(p, 1);
        }
      }
      this.groupeInput.nativeElement.value = '';
      this.fruitCtrl.setValue(null);
    })
  }

  private _filter(value: string): Groupe[] {
    return this.user.appGroupeListDispo.filter(fruit => fruit.code.indexOf(value) === 0);
  }
  onFileChange(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      let reader = new FileReader();
      this.signUpForm.controls['image'].setValue(file);
      reader.readAsDataURL(file);
      reader.onloadend = (e) => {
        this.signUpForm.get('image').setValue(reader.result.toString().split(',')[1]);
        this.url = reader.result.toString();
      };
    }
  }
  onChangePass() {
    this.changePass = !this.changePass;
  }
  prepareUserForUpdate() {
    // this.userTest.profilImage = this.url;
    this.userTest = new User();
    this.userTest.username = this.signUpForm.value.userName;
    this.userTest.firstName = this.signUpForm.value.userfirstName;
    this.userTest.lastName = this.signUpForm.value.userLastName;
    this.userTest.email = this.signUpForm.value.userMail;
    this.userTest.address = this.signUpForm.value.userAdresse;
    this.setActif();
    this.userTest.sexe = this.signUpForm.value.selectedGender;
    this.userTest.valid = this.active;
    this.userTest.id = this.user.id;
  }
  onEditUser() {
    let groupeTest: Array<String> = new Array;
    this.user.appGroupeList.forEach(x => groupeTest.push(x.code));
    this.prepareUserForUpdate();
    if (this.changePass) {
      if (this.confPass === this.newPass) {
        this.user.password = this.newPass;
        this.usersService.saveDataAndMore(this.user).subscribe();
        localStorage.setItem(PathName.IMG_PROFILE, this.url);
        this.router.navigateByUrl('home', { relativeTo: this.route });
        this.messageService.openSnackBar("EDITING  the user was a success", SEVERITY_TYPES.INFO_SEV);
        // this.authenticationService.onLogOut();
      }
      else {
        this.messageService.openSnackBar("mot de passe et confirmation sont different", SEVERITY_TYPES.WARNING_SEV);
      }
    } else {
      this.usersService.saveDataE(this.userTest, groupeTest, true).subscribe(
        () => {
          this.messageService.openSnackBar("Creation of the user was a success", SEVERITY_TYPES.INFO_SEV);

        }
      );
      //localStorage.setItem(PathName.IMG_PROFILE, this.url);
      //  this.router.navigateByUrl('home', { relativeTo: this.route });
    }
  }
  setActif() {
    if (this.selectedState === 'Actif') {
      this.active = true;
    }
    if (this.selectedState === 'blocked') {
      this.active = false;
    }

  }
  checkLength(): boolean {
    let t = this.signUpForm.controls['userName'].value;
    if (t !== null) {
      if (t.length > 4) {
        return false;
      }
    }
    return true;
  }
}

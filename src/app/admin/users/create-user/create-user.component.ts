import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from 'src/app/auth/authentication.service';
import { Groupe } from 'src/app/buisness/models/Groupe';
import { User } from 'src/app/buisness/models/User';
import { GroupeService } from 'src/app/buisness/services/groupe.service';
import { MessageService } from 'src/app/buisness/services/message.service';
import { UserService } from 'src/app/buisness/services/user.service';
import { GenderUser } from 'src/app/helpers/Gender-user';
import { SEVERITY_TYPES } from 'src/app/helpers/severities.name';
interface Gender {
  code: string;
  icon: string;
}
@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: { showError: true, displayDefaultIndicatorType: false }
  }]
})
export class CreateUserComponent implements OnInit {
  constructor(private groupeService: GroupeService,
    private userService: UserService,
    private authService: AuthenticationService,
    private messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute) {
    const currentYear = new Date().getFullYear();
    this.minDate = new Date(currentYear - 100, 0, 1);
    this.maxDate = new Date(currentYear - 14, 11, 31);
  }
  minDate: Date;
  maxDate: Date;
  formArray: FormGroup[];
  groupes: Groupe[];
  genre = new FormControl();
  url: string;
  genders: Gender[] = [];
  signUpForm: FormGroup = new FormGroup({
    'loginInfo': new FormGroup({
      'userMail': new FormControl(null, [Validators.required])
    }),
    'civility': new FormGroup({
      'userfirstName': new FormControl(null, [Validators.required]),
      'userLastName': new FormControl(null, [Validators.required]),
      'userAdresse': new FormControl(null, [Validators.required]),
      'dateNaissance': new FormControl(null, [Validators.required]),
      'gender': new FormControl({ code: GenderUser.MALE, icon: "perm_identity" })
    }),
    'accountOptions': new FormGroup({
      'groupes': new FormControl(null),
      'valid': new FormControl(null),
      'userName': new FormControl(null, [Validators.required]),
      'image': new FormControl(null),
    }),
    'selectedGender': new FormControl(null),
    'signUpRole': new FormControl(null),
    'date': new FormControl((null)),
  });;
  ngOnInit() {
    this.groupeService.findAllGroupes().subscribe(data => {
      this.groupes = data;
    });
    this.genders.push({ code: GenderUser.MALE, icon: "perm_identity" });
    this.genders.push({ code: GenderUser.FEMALE, icon: "pregnant_woman" });
    this.genders.push({ code: GenderUser.OTHER, icon: "help" });
  }
  onFileChange(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      let reader = new FileReader();
      this.signUpForm.get('accountOptions.image').setValue(file);
      reader.readAsDataURL(file);
      reader.onloadend = (e) => {
        this.signUpForm.get('accountOptions.image').setValue(reader.result.toString().split(',')[1]);
        this.url = reader.result.toString();
      };
    }
  }
  get civility(): FormArray {
    return this.signUpForm.get('civility') as FormArray;
  }
  getSelectValue() {
    if (this.signUpForm.get('civility') !== undefined) {
      if (this.signUpForm.get('civility').value !== null) {
        let gender = this.signUpForm.get('civility.gender').value;
        this.signUpForm.get('selectedGender').setValue(gender.code);
        return gender.code;
      } else {
        return " ";
      }
    }
  }
  onSignUp() {
    let user = new User();
    let roles: string[] = [];
    let gpes: Groupe[] = [];
    let active: boolean;
    let date: Date;
    gpes = this.signUpForm.get('accountOptions.groupes').value;
    if (this.signUpForm.get('accountOptions.valid').value === 'Actif') {
      active = true;
      this.signUpForm.get('accountOptions.valid').setValue(active);
    }
    if (this.signUpForm.get('accountOptions.valid').value === 'blocked') {
      active = false;
      this.signUpForm.get('accountOptions.valid').setValue(active);
    }
    gpes.forEach(x => {
      roles.push(x.code);
    })
    this.signUpForm.get('signUpRole').setValue(roles);
    user.username = this.signUpForm.value.userName;
    user.profilImage = this.url;
    user.firstName = this.signUpForm.value.userfirstName;
    user.lastName = this.signUpForm.value.userLastName;
    user.email = this.signUpForm.value.userMail;
    user.dateNaissance = this.signUpForm.value.dateNaissance;
    user.address = this.signUpForm.value.userAdresse;
    user.password = this.signUpForm.value.password;
    this.signUpForm.get("accountOptions.image").setValue(this.url);
    this.userService.createUser(this.signUpForm).subscribe(
      () => {
        this.messageService.openSnackBar("Creation of the user was a success", SEVERITY_TYPES.INFO_SEV);
      
      }
    );
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
  isLoggedInFilled(){
    if(this.signUpForm.get('loginInfo.userMail').value !== null){
    return true;
  }else{
    return false;
  }
}
isCivilityFilled(){
  if(this.signUpForm.get('civility.userfirstName').value !== null && 
  this.signUpForm.get('civility.userLastName').value !== null &&
  this.signUpForm.get('civility.userAdresse').value !== null
  &&
  this.signUpForm.get('civility.dateNaissance').value !== null
  &&
  this.signUpForm.get('civility.gender').value !== null){
  return true;
}else{
  return false;
}
}
isAccountOptionsFilled(){
  if(this.signUpForm.get('accountOptions.userName').value !== null && 
  this.signUpForm.get('accountOptions.image').value !== null 
  && 
  this.signUpForm.get('accountOptions.groupes').value !== null 
  && 
  this.signUpForm.get('accountOptions.valid').value !== null ){
  return true;
}else{
  return false;
}
}
}

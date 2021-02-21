import { Menu } from './Menu';
import { Module } from './Module';
import { User } from './User';

export class Groupe {
    id: number;
    code: string;
    label: string;
    active : boolean;    
    module : Module;
    menuGroupe : Array<Menu>;
    menuGroupeDispo : Array<Menu>;
    appUsers : Array<User>; 
    constructor( code: string,
         label: string,
      ) {
this.code = code;
this.label = label ;
      }
}
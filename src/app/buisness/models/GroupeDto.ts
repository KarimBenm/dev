import { Groupe } from './Groupe';
import { Menu } from './Menu';
import { Module } from './Module';
import { User } from './User';

export class GroupeDto {
    id: number;
    code: string;
    label: string;
    active : boolean;    
    module : Module;
    menuGroupe : Array<Menu>;
    appUsers : Array<User>;  
}
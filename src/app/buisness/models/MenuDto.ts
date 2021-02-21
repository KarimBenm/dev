import { Groupe } from './Groupe';
import { Menu } from './Menu';
import { Module } from './Module';
import { User } from './User';

export class MenuDto {
    id: number;
    label: string;
    urls : string;   
    menuGroupeList : Array<Groupe>;
    sousMenuList : Array<Menu>;
    sousMenuDispoList : Array<Menu>;
    routers: string;
    icon: string;
    color: string;
    parents: boolean;
    menu: Menu;
}
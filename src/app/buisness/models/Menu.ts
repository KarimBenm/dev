import { Groupe } from './Groupe';

export class Menu {
    id: number;
    label: string;    
    menuGroupeList : Array<Groupe>;
    sousMenuList : Array<Menu>;
    sousMenuDispoList : Array<Menu>;
    routers: string;
    urls: string;
    icon: string;
    color: string;
    parents: boolean;
    menu: Menu;
}
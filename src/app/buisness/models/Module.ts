import { Groupe } from './Groupe';

export class Module {
    id: number;
    code: string;
    label: string; 
    routers: string;
    urls: string;
    name : string;
    icon: string;
    color: string;
    col : number;
    raw : number ;
    groupeList : Array<Groupe>;   
}
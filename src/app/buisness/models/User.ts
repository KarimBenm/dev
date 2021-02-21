import { Groupe } from './Groupe';

export class User {
    id: number;
    username: string;
    firstName: string;
    lastName: string;
    address: string;
    sexe :string;
    ipAddress: string;
    dateNaissance : Date;
    profilImage : string;
    admin: boolean;
    valid: boolean;
    phone: string;
    password : string;
    email: string;
    appGroupeList: Array<Groupe>;    
    appGroupeListDispo: Array<Groupe>;    
}
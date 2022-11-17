import { Roles } from "./roles";

export type Accounts ={
    id:string;
    userId:string;
    hostName:string;
    isActive:boolean;
    role:Roles;
}
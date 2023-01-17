import { Applications } from "./applications";
import { Methods } from "./methods";

export type Connections ={
    id:string;
    application:Applications
    method:Methods
    name:string;
    createdAt:Date
}
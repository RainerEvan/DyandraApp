import { Connections } from "./connections";

export type SourcePaths ={
    id:string;
    connection:Connections
    name:string;
    path:string;
    username:string;
    password:string;
    createdAt:Date
    updatedAt:Date
}
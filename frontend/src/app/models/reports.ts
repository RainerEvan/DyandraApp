import { Connections } from "./connections";
import { SourcePaths } from "./sourcepaths";

export interface Reports{
    id:string
    connection:Connections;
    sourcePath:SourcePaths;
    query:string;
    title:string;
    reportConfig:string;
    reportId:string;
    createdAt:Date;
    updatedAt:Date;
}
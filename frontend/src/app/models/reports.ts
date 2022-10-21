import { Connections } from "./connections";
import { SourcePaths } from "./sourcepaths";

export interface Reports{
    id:string
    connection:Connections;
    sourcePath:SourcePaths;
    title:string;
    report:string;
    reportId:string;
    createdAt:Date;
    updatedAt:Date;
}
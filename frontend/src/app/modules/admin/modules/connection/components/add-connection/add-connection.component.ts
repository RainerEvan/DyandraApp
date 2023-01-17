import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Applications } from 'src/app/models/applications';
import { Methods } from 'src/app/models/methods';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { ApplicationService } from 'src/app/services/application/application.service';
import { ConnectionService } from 'src/app/services/connection/connection.service';
import { MethodService } from 'src/app/services/method/method.service';

@Component({
  selector: 'app-add-connection',
  templateUrl: './add-connection.component.html',
  styleUrls: ['./add-connection.component.css']
})
export class AddConnectionComponent implements OnInit {

  connectionForm: FormGroup;
  isConnectionFormSubmitted: boolean = false;
  applications: Applications[];
  methods: Methods[];
  
  constructor(public ref: DynamicDialogRef, private dialogService:DialogService, private connectionService: ConnectionService, private applicationService:ApplicationService ,private methodService:MethodService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generateConnectionForm();
  }

  public getAllApplications(): void{
    this.applicationService.getAllApplications().subscribe({
      next:(response:Applications[])=>{
        this.applications = response;
      },
      error:(error:any)=>{
        console.log(error);
      }
    });
  }

  public getAllMethods(): void{
    this.methodService.getAllMethods().subscribe({
      next:(response:Methods[])=>{
        this.methods = response;
      },
      error:(error:any)=>{
        console.log(error);
      }
    });
  }

  public addConnection(): void{
    if(this.connectionForm.valid){
      const connection = this.connectionForm.value;

      this.connectionService.addConnection(connection).subscribe({
        next: (result: any) => {
          this.isConnectionFormSubmitted = true;
          this.ref.close(this.isConnectionFormSubmitted);
          this.showResultDialog("Success","Connection has been created successfully");
        },
        error: (error: any) => {
          this.isConnectionFormSubmitted = false;
          this.ref.close(this.isConnectionFormSubmitted);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    } 
  }

  generateConnectionForm(){
    this.connectionForm = this.formBuilder.group({
      applicationId: [null, [Validators.required]],
      methodName: [null, [Validators.required]],
      name: [null, [Validators.required]],
    });
    this.getAllApplications();
    this.getAllMethods();
  }

  
  showResultDialog(title:string, message:string){
    this.ref = this.dialogService.open(ResultDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });
  }
}

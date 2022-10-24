import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { Applications } from 'src/app/models/applications';
import { Methods } from 'src/app/models/methods';
import { ApplicationService } from 'src/app/services/application/application.service';
import { ConnectionService } from 'src/app/services/connection/connection.service';
import { MethodService } from 'src/app/services/method/method.service';

@Component({
  selector: 'app-add-connection',
  templateUrl: './add-connection.component.html',
  styleUrls: ['./add-connection.component.scss']
})
export class AddConnectionComponent implements OnInit {

  connectionForm: FormGroup;
  isConnectionFormSubmitted: boolean = false;
  applications: Applications[];
  methods: Methods[];
  
  constructor(public ref: DynamicDialogRef,private connectionService: ConnectionService, private applicationService:ApplicationService ,private methodService:MethodService, private formBuilder: FormBuilder) { }

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
          console.log(result);
        },
        error: (error: any) => {
          this.isConnectionFormSubmitted = false;
          this.ref.close(this.isConnectionFormSubmitted);
          console.log(error);
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

  get applicationId(){
    return this.connectionForm.get('applicationId');
  }
  get methodName(){
    return this.connectionForm.get('methodName');
  }
  get name(){
    return this.connectionForm.get('name');
  }

}

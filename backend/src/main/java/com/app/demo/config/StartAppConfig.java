package com.app.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.demo.data.EMethod;
import com.app.demo.model.Accounts;
import com.app.demo.model.Applications;
import com.app.demo.model.Connections;
import com.app.demo.model.Methods;
import com.app.demo.model.Reports;
import com.app.demo.model.Roles;
import com.app.demo.model.SourcePaths;
import com.app.demo.payload.request.AccountRequest;
import com.app.demo.payload.request.ApplicationRequest;
import com.app.demo.payload.request.ConnectionRequest;
import com.app.demo.payload.request.ReportRequest;
import com.app.demo.payload.request.SourcePathRequest;
import com.app.demo.repository.MethodRepository;
import com.app.demo.repository.RoleRepository;
import com.app.demo.service.AccountService;
import com.app.demo.service.ApplicationService;
import com.app.demo.service.ConnectionService;
import com.app.demo.service.ReportService;
import com.app.demo.service.SourcePathService;

@Configuration
public class StartAppConfig {
    @Bean
    CommandLineRunner commandLineRunner(MethodRepository methodRepository, ApplicationService applicationService, ConnectionService connectionService, ReportService reportService, SourcePathService sourcePathService, RoleRepository roleRepository, AccountService accountService){
        return args -> {
            Methods method1 = new Methods();
            method1.setName(EMethod.API);
            methodRepository.save(method1);

            Methods method2 = new Methods();
            method2.setName(EMethod.API_GATEAWAY);
            methodRepository.save(method2);

            Methods method3 = new Methods();
            method3.setName(EMethod.DIRECT_DB);
            methodRepository.save(method3);

            Methods method4 = new Methods();
            method4.setName(EMethod.LOCAL_FILES);
            methodRepository.save(method4);

            Roles role = new Roles();
            role.setName("ADMIN");
            roleRepository.save(role);

            AccountRequest accountReq = new AccountRequest("admin", "pass123", "insomnia", role.getId());
            Accounts account = accountService.addAccount(accountReq);

            ApplicationRequest applicationReq = new ApplicationRequest("ICOS", "ICOS");
            Applications application = applicationService.addApplication(applicationReq);

            ApplicationRequest applicationReq2 = new ApplicationRequest("SMILE", "SMLE");
            Applications application2 = applicationService.addApplication(applicationReq2);

            ConnectionRequest connectionReq = new ConnectionRequest(application.getId(), EMethod.API,"ICOS-API");
            Connections connection = connectionService.addConnection(connectionReq);

            SourcePathRequest sourcePathReq = new SourcePathRequest(connection.getId(), "API URL", "http://localhost:8081/api/orders/all", null, null);
            SourcePaths sourcePath = sourcePathService.addSourcePath(sourcePathReq);

            ReportRequest reportReq = new ReportRequest(connection.getId(), sourcePath.getId(), null, "Report 1",null);
            Reports report = reportService.addReport(reportReq);

            ConnectionRequest connectionReq2 = new ConnectionRequest(application.getId(), EMethod.LOCAL_FILES,"ICOS-FILES");
            Connections connection2 = connectionService.addConnection(connectionReq2);

            // SourcePathRequest sourcePathReq2= new SourcePathRequest(connection2.getId(), "FILE URL", "C:/Users/u545587/Downloads/data.json", null, null);
            // SourcePaths sourcePath2 = sourcePathService.addSourcePath(sourcePathReq2);

            SourcePathRequest sourcePathReq2= new SourcePathRequest(connection2.getId(), "FILE URL", "D:/Download/data.json", null, null);
            SourcePaths sourcePath2 = sourcePathService.addSourcePath(sourcePathReq2);

            ReportRequest reportReq2 = new ReportRequest(connection2.getId(), sourcePath2.getId(), null, "Report 2",null);
            Reports report2 = reportService.addReport(reportReq2);

            ConnectionRequest connectionReq3 = new ConnectionRequest(application2.getId(), EMethod.LOCAL_FILES,"SMILE-FILES");
            Connections connection3 = connectionService.addConnection(connectionReq3);

            // SourcePathRequest sourcePathReq3= new SourcePathRequest(connection3.getId(), "FILE URL", "C:/Users/u545587/Downloads/data.json", null, null);
            // SourcePaths sourcePath3 = sourcePathService.addSourcePath(sourcePathReq3);
            
            SourcePathRequest sourcePathReq3= new SourcePathRequest(connection3.getId(), "FILE URL", "D:/Download/data.csv", null, null);
            SourcePaths sourcePath3 = sourcePathService.addSourcePath(sourcePathReq3);

            ReportRequest reportReq3 = new ReportRequest(connection3.getId(), sourcePath3.getId(), null, "Report 3",null);
            Reports report3 = reportService.addReport(reportReq3);
        };
    }
}

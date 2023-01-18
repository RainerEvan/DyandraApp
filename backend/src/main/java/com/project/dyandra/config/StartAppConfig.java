// package com.project.dyandra.config;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import com.project.dyandra.data.EMethod;
// import com.project.dyandra.model.Accounts;
// import com.project.dyandra.model.Applications;
// import com.project.dyandra.model.Connections;
// import com.project.dyandra.model.Methods;
// import com.project.dyandra.model.Reports;
// import com.project.dyandra.model.Roles;
// import com.project.dyandra.model.SourcePaths;
// import com.project.dyandra.payload.request.AccountRequest;
// import com.project.dyandra.payload.request.ApplicationRequest;
// import com.project.dyandra.payload.request.ConnectionRequest;
// import com.project.dyandra.payload.request.ReportRequest;
// import com.project.dyandra.payload.request.SourcePathRequest;
// import com.project.dyandra.repository.MethodRepository;
// import com.project.dyandra.repository.RoleRepository;
// import com.project.dyandra.service.AccountService;
// import com.project.dyandra.service.ApplicationService;
// import com.project.dyandra.service.ConnectionService;
// import com.project.dyandra.service.ReportService;
// import com.project.dyandra.service.SourcePathService;

// @Configuration
// public class StartAppConfig {
//     @Bean
//     CommandLineRunner commandLineRunner(MethodRepository methodRepository, ApplicationService applicationService, ConnectionService connectionService, ReportService reportService, SourcePathService sourcePathService, RoleRepository roleRepository, AccountService accountService){
//         return args -> {
//             Methods method1 = new Methods();
//             method1.setName(EMethod.API);
//             methodRepository.save(method1);

//             Methods method2 = new Methods();
//             method2.setName(EMethod.API_GATEAWAY);
//             methodRepository.save(method2);

//             Methods method3 = new Methods();
//             method3.setName(EMethod.DIRECT_DB);
//             methodRepository.save(method3);

//             Methods method4 = new Methods();
//             method4.setName(EMethod.LOCAL_FILES);
//             methodRepository.save(method4);

//             Roles role = new Roles();
//             role.setName("ADMIN");
//             roleRepository.save(role);

//             AccountRequest accountReq = new AccountRequest("admin", "pass123", "insomnia", role.getId());
//             Accounts account = accountService.addAccount(accountReq);

//             ApplicationRequest applicationReq = new ApplicationRequest("ICOS", "ICOS");
//             Applications application = applicationService.addApplication(applicationReq);

//             ApplicationRequest applicationReq2 = new ApplicationRequest("SMILE", "SMLE");
//             Applications application2 = applicationService.addApplication(applicationReq2);

//             ConnectionRequest connectionReq2 = new ConnectionRequest(application.getId(), EMethod.LOCAL_FILES,"ICOS-FILES");
//             Connections connection2 = connectionService.addConnection(connectionReq2);

//             SourcePathRequest sourcePathReq2= new SourcePathRequest(connection2.getId(), "FILE URL", "C:/Users/u545587/Downloads/data.json", null, null);
//             SourcePaths sourcePath2 = sourcePathService.addSourcePath(sourcePathReq2);

//             // SourcePathRequest sourcePathReq2= new SourcePathRequest(connection2.getId(), "FILE URL", "D:/Download/data.json", null, null);
//             // SourcePaths sourcePath2 = sourcePathService.addSourcePath(sourcePathReq2);

//             ReportRequest reportReq2 = new ReportRequest(connection2.getId(), sourcePath2.getId(), null, "Report 2",null);
//             Reports report2 = reportService.addReport(reportReq2);

//             ConnectionRequest connectionReq3 = new ConnectionRequest(application2.getId(), EMethod.DIRECT_DB,"SMILE-DB");
//             Connections connection3 = connectionService.addConnection(connectionReq3);

//             SourcePathRequest sourcePathReq3= new SourcePathRequest(connection3.getId(), "SMILE DB 1", "jdbc:postgresql://localhost:5432/dummy", "postgres", "Bojongloa90");
//             SourcePaths sourcePath3 = sourcePathService.addSourcePath(sourcePathReq3);
            
//             // SourcePathRequest sourcePathReq3= new SourcePathRequest(connection3.getId(), "FILE URL", "D:/Download/data.csv", null, null);
//             // SourcePaths sourcePath3 = sourcePathService.addSourcePath(sourcePathReq3);

//             ReportRequest reportReq3 = new ReportRequest(connection3.getId(), sourcePath3.getId(), "SELECT * FROM appraisals", "Report 3",null);
//             Reports report3 = reportService.addReport(reportReq3);
//         };
//     }
// }

package com.app.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.demo.data.EMethod;
import com.app.demo.model.Applications;
import com.app.demo.model.Connections;
import com.app.demo.model.Methods;
import com.app.demo.model.Reports;
import com.app.demo.model.SourcePaths;
import com.app.demo.payload.request.ApplicationRequest;
import com.app.demo.payload.request.ConnectionRequest;
import com.app.demo.payload.request.ReportRequest;
import com.app.demo.payload.request.SourcePathRequest;
import com.app.demo.repository.MethodRepository;
import com.app.demo.service.ApplicationService;
import com.app.demo.service.ConnectionService;
import com.app.demo.service.ReportService;
import com.app.demo.service.SourcePathService;

@Configuration
public class StartAppConfig {
    @Bean
    CommandLineRunner commandLineRunner(MethodRepository methodRepository, ApplicationService applicationService, ConnectionService connectionService, ReportService reportService, SourcePathService sourcePathService){
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

            ApplicationRequest applicationReq = new ApplicationRequest("ICOS", "ICOS");
            Applications application = applicationService.addApplication(applicationReq);

            ApplicationRequest applicationReq2 = new ApplicationRequest("SMILE", "SMLE");
            Applications application2 = applicationService.addApplication(applicationReq2);

            ConnectionRequest connectionReq = new ConnectionRequest(application.getId(), EMethod.API);
            Connections connection = connectionService.addConnection(connectionReq);

            SourcePathRequest sourcePathReq = new SourcePathRequest(connection.getId(), "/contoh");
            SourcePaths sourcePath = sourcePathService.addSourcePath(sourcePathReq);

            ReportRequest reportReq = new ReportRequest(connection.getId(), sourcePath.getId(), "SELECT * FROM", "Report Oktober", "Contoh");
            Reports report = reportService.addReport(reportReq);

        };
    }
}

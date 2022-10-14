package com.app.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.demo.data.EMethod;
import com.app.demo.model.Methods;
import com.app.demo.repository.MethodRepository;

@Configuration
public class StartAppConfig {
    @Bean
    CommandLineRunner commandLineRunner(MethodRepository methodRepository){
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
        };
    }
}

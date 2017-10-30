package com.ppfly.demo;

import com.ppfly.demo.activiti.modeler.JsonpCallbackFilter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@SpringBootApplication
@ComponentScan({"org.activiti.rest.diagram", "com.ppfly.demo"})
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
})
public class PpflyActivitiDemoApplication {


    @Autowired
    ProcessEngine processEngine;

    @RequestMapping("/")
    public String index(Model model) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<org.activiti.engine.repository.Model> models = repositoryService.createModelQuery().list();
        model.addAttribute("models", models);
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(PpflyActivitiDemoApplication.class, args);
    }

    @Bean
    public JsonpCallbackFilter filter() {
        return new JsonpCallbackFilter();
    }
}

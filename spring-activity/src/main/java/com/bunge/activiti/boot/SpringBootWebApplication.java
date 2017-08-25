package com.bunge.activiti.boot;

import javax.sql.DataSource;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@Configuration
public class SpringBootWebApplication {

	@Value("${bunge.activiti.mysql.username}")
	private String mySqlUsername;
	
	@Value("${bunge.activiti.mysql.password}")
	private String mysqlPassword;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner  init(final RepositoryService repositoryService,
			final RuntimeService runtimeService, final TaskService taskService){
		return new CommandLineRunner() {
			
			@Override
			public void run(String... arg0) throws Exception {
				System.out.println("Number of process definitions : "+repositoryService.createProcessDefinitionQuery().count());
				System.out.println("Number of tasks : "+taskService.createTaskQuery().count());
				System.out.println("Number of tasks after process start : "+taskService.createTaskQuery().count());
			}
		};
	}
	
	@Bean
	public DataSource dbDatasource(){
		return DataSourceBuilder.create().url("jdbc:mysql://localhost:3306/testdb?characterEncoding=UTF-8")
				.username(mySqlUsername)
				.password(mysqlPassword)
				.driverClassName("com.mysql.jdbc.Driver")
				.build();
	}

}

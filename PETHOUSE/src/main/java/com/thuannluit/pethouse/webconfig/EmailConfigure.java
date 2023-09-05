package com.thuannluit.pethouse.webconfig;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:email.properties")
@ComponentScan(basePackages = { "com.thuannluit.pethouse.*" })
public class EmailConfigure {
	
	@Autowired
	private Environment environment;

	@Bean
	public JavaMailSenderImpl mailSender() {
		  JavaMailSenderImpl sender = new JavaMailSenderImpl();
		  sender.setHost(environment.getRequiredProperty("spring.mail.host"));
		  sender.setPort(Integer.valueOf(environment.getRequiredProperty("spring.mail.port")));
		  sender.setUsername(environment.getRequiredProperty("spring.mail.username"));
		  sender.setPassword(environment.getRequiredProperty("spring.mail.password"));
		  sender.setProtocol(environment.getRequiredProperty("spring.mail.properties.mail.transport.protocol"));
		  Properties javaMailProps = new Properties();
		  javaMailProps.setProperty("mail.smtp.auth", environment.getRequiredProperty("spring.mail.properties.mail.smtp.auth"));
		  javaMailProps.setProperty("mail.smtp.starttls.enable", environment.getRequiredProperty("spring.mail.properties.mail.smtp.starttls.enable"));
		  javaMailProps.setProperty("mail.smtp.timeout", "25000");
		  sender.setJavaMailProperties(javaMailProps);

		return sender;
	}
}

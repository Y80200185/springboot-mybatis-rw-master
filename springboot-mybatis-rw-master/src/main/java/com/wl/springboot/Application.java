package com.wl.springboot;

import ch.qos.logback.classic.pattern.MessageConverter;
import com.wl.springboot.service.RedisService;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

//@EnableAutoConfiguration
@ServletComponentScan
@SpringBootApplication
@ImportResource(locations = {"classpath:applicationContext.xml"})
public class Application extends SpringBootServletInitializer implements ApplicationRunner {

	 @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

	 
	 public static void main(String[] args) throws Exception {
	        SpringApplication.run(Application.class, args);
	    }

//	public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
//		configurableEmbeddedServletContainer.setPort(9097);
//	}
	@Autowired
	private RedisService redisService;

	/**
	 * redis初始化各商品的库存量
	 * @param args
	 * @throws Exception
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		redisService.put("watch", 10, 20);
	}

//	@Bean
//	public MessageConverter jsonMessageConverter(){
//		return new Jackson2JsonMessageConverter();
//	}


//	@Override
//	public void run(String... strings) throws Exception {
//
//	}
}

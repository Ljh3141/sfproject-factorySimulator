package yonam2023.stubFactoryServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import yonam2023.stubFactoryServer.service.StubRunning;

@SpringBootApplication
public class StubFactoryServerApplication {
	public static void main(String[] args) {

		SpringApplication.run(StubFactoryServerApplication.class, args);
	}


}

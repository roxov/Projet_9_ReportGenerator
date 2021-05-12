package fr.asterox.ReportGenerator;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("fr.asterox")
@SpringBootApplication
public class ReportGeneratorApplication {
	private static final Logger LOGGER = LogManager.getLogger(ReportGeneratorApplication.class);

	public static void main(String[] args) throws IOException {
		LOGGER.info("Initializing ReportGenerator");
		SpringApplication.run(ReportGeneratorApplication.class, args);
	}
}

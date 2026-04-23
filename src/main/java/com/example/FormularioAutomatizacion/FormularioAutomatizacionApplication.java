package com.example.FormularioAutomatizacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FormularioAutomatizacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormularioAutomatizacionApplication.class, args);
	}

}

package br.com.tqi.evolution;

import br.com.tqi.evolution.domain.Client;
import br.com.tqi.evolution.domain.Role;
import br.com.tqi.evolution.services.ClientService;
import br.com.tqi.evolution.services.ClientServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class EvolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvolutionApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run (ClientService clientService) {
		return args -> {
			clientService.saveClient(new Client(null, "Marcos Adriano", "marcos@email.com", "123", "99999999999", "888888888", "Rua", 500.0, new ArrayList<>(), new ArrayList<>()));
			clientService.saveClient(new Client(null, "Diegod", "diego@email.com", "123", "99999999999", "888888888", "Rua", 500.0, new ArrayList<>(), new ArrayList<>()));
			clientService.saveClient(new Client(null, "Angelin", "angelin@email.com", "123", "99999999999", "888888888", "Rua", 500.0, new ArrayList<>(), new ArrayList<>()));

			clientService.saveRole(new Role(null, "ROLE_CLIENT"));
			clientService.saveRole(new Role(null, "ROLE_ADMIN"));

			clientService.addRoleToClient("marcos@email.com", "ROLE_CLIENT");
			clientService.addRoleToClient("marcos@email.com", "ROLE_ADMIN");
			clientService.addRoleToClient("diego@email.com", "ROLE_CLIENT");
			clientService.addRoleToClient("angelin@email.com", "ROLE_CLIENT");
		};
	}

}

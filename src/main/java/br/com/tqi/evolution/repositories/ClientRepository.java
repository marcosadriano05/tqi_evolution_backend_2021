package br.com.tqi.evolution.repositories;

import br.com.tqi.evolution.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail (String email);
}

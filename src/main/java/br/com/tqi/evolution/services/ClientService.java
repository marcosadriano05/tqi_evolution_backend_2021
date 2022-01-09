package br.com.tqi.evolution.services;

import br.com.tqi.evolution.domain.Borrow;
import br.com.tqi.evolution.domain.Role;
import br.com.tqi.evolution.domain.Client;

import java.time.ZonedDateTime;
import java.util.List;

public interface ClientService {
    Client saveClient (Client client);
    Role saveRole (Role role);
    void addRoleToClient (String email, String roleName);
    Client getClient (String email);
    Client getClientById (Long id) throws Exception;
    List<Client> getClients ();
    void requestBorrowing (String email, Double value, ZonedDateTime firstInstallmentDate, int numberOfInstallments) throws Exception;
    Borrow getBorrow (Long borrowId) throws Exception;
}

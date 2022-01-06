package br.com.tqi.evolution.services;

import br.com.tqi.evolution.domain.Role;
import br.com.tqi.evolution.domain.Client;

import java.util.List;

public interface ClientService {
    Client saveClient (Client client);
    Role saveRole (Role role);
    void addRoleToClient (String email, String roleName);
    Client getClient (String email);
    List<Client> getClients ();
}

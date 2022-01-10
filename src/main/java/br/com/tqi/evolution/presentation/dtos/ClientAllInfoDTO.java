package br.com.tqi.evolution.presentation.dtos;

import br.com.tqi.evolution.domain.Borrow;
import br.com.tqi.evolution.domain.Client;
import br.com.tqi.evolution.domain.Role;
import lombok.Data;

import java.util.Collection;

@Data
public class ClientAllInfoDTO {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String rg;
    private String address;
    private Double rent;
    private Collection<Role> roles;
    private Collection<Borrow> borrows;

    public ClientAllInfoDTO (Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
        this.cpf = client.getCpf();
        this.rg = client.getRg();
        this.address = client.getAddress();
        this.rent = client.getRent();
        this.roles = client.getRoles();
        this.borrows = client.getBorrows();
    }
}

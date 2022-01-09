package br.com.tqi.evolution.presentation.dtos;

import br.com.tqi.evolution.domain.Client;
import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String rg;
    private String address;
    private Double rent;

    public ClientDTO (Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
        this.cpf = client.getCpf();
        this.rg = client.getRg();
        this.address = client.getAddress();
        this.rent = client.getRent();
    }
}

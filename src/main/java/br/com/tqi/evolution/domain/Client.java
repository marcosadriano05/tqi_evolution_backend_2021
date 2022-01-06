package br.com.tqi.evolution.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String cpf;
    private String rg;
    private String address;
    private Double rent;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Borrow> borrows = new ArrayList<>();
}

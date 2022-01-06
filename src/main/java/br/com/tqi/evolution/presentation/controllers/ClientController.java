package br.com.tqi.evolution.presentation.controllers;

import br.com.tqi.evolution.domain.Borrow;
import br.com.tqi.evolution.domain.Client;
import br.com.tqi.evolution.domain.Role;
import br.com.tqi.evolution.presentation.dtos.RequestBorrowingDTO;
import br.com.tqi.evolution.presentation.dtos.RoleToClientDTO;
import br.com.tqi.evolution.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    ResponseEntity<List<Client>> getClients () {
        return ResponseEntity.ok().body(clientService.getClients());
    }

    @PostMapping
    ResponseEntity<Client> saveClient (@RequestBody Client client) {
        return ResponseEntity.ok().body(clientService.saveClient(client));
    }

    @PostMapping("/role")
    ResponseEntity<Role> saveRole (@RequestBody Role role) {
        return ResponseEntity.ok().body(clientService.saveRole(role));
    }

    @PostMapping("/roleto")
    ResponseEntity<?> addRoleToClient (@RequestBody RoleToClientDTO roleToClientDTO) {
        clientService.addRoleToClient(roleToClientDTO.getEmail(), roleToClientDTO.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/borrow")
    ResponseEntity<?> requestBorrowing (@RequestBody RequestBorrowingDTO requestBorrowingDTO, Authentication authentication) {
        try {
            String email = authentication.getName();
            System.out.println(email);
            Date date = requestBorrowingDTO.getFirstInstallmentDate();
            ZonedDateTime dateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
            clientService.requestBorrowing(email, requestBorrowingDTO.getValue(), dateTime, requestBorrowingDTO.getNumberOfInstallments());
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/borrow")
    ResponseEntity<List<Borrow>> getBorrows (Authentication authentication) {
        String email = authentication.getName();
        System.out.println(email);
        List<Borrow> borrows = new ArrayList<>(clientService.getClient(email).getBorrows());
        return ResponseEntity.ok().body(borrows);
    }
}

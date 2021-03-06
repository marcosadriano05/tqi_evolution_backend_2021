package br.com.tqi.evolution.presentation.controllers;

import br.com.tqi.evolution.domain.Borrow;
import br.com.tqi.evolution.domain.Client;
import br.com.tqi.evolution.domain.Role;
import br.com.tqi.evolution.presentation.dtos.ClientAllInfoDTO;
import br.com.tqi.evolution.presentation.dtos.ClientDTO;
import br.com.tqi.evolution.presentation.dtos.RequestBorrowingDTO;
import br.com.tqi.evolution.presentation.dtos.RoleToClientDTO;
import br.com.tqi.evolution.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @Value("${baseUrl}")
    private String baseUrl;

    @GetMapping
    ResponseEntity<List<ClientDTO>> getClients () {
        List<Client> clients = clientService.getClients();
        List<ClientDTO> clientsDTO = clients.stream().map(ClientDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(clientsDTO);
    }

    @PostMapping
    ResponseEntity<ClientDTO> saveClient (@RequestBody Client client) {
        Client savedClient = clientService.saveClient(client);
        URI uri = URI.create(baseUrl + "/client/" + savedClient.getId());
        return ResponseEntity.created(uri).body(new ClientDTO(savedClient));
    }

    @GetMapping("/{id}")
    ResponseEntity<ClientAllInfoDTO> getClient (Authentication authentication, @PathVariable Long id) {
        try {
            Client client = clientService.getClientById(id);
            Client clientWithMadeRequest = clientService.getClient((String) authentication.getPrincipal());
            List<String> roleNames = clientWithMadeRequest.getRoles().stream().map(Role::getName).collect(Collectors.toList());
            if (!(roleNames.contains("ROLE_ADMIN") || client.getEmail().equals(authentication.getPrincipal()))) {
                return ResponseEntity.status(403).build();
            }
            return ResponseEntity.ok().body(new ClientAllInfoDTO(client));
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
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
            Date date = requestBorrowingDTO.getFirstInstallmentDate();
            ZonedDateTime dateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
            clientService.requestBorrowing(email, requestBorrowingDTO.getValue(), dateTime, requestBorrowingDTO.getNumberOfInstallments());
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/borrow")
    ResponseEntity<List<Borrow>> getBorrows (Authentication authentication) {
        String email = authentication.getName();
        List<Borrow> borrows = new ArrayList<>(clientService.getClient(email).getBorrows());
        return ResponseEntity.ok().body(borrows);
    }

    @GetMapping("/borrow/{borrowId}")
    ResponseEntity<Borrow> getBorrow (@PathVariable Long borrowId) {
        try {
            Borrow borrow = clientService.getBorrow(borrowId);
            return ResponseEntity.ok().body(borrow);
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }
}

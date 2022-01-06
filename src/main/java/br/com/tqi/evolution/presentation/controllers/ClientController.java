package br.com.tqi.evolution.presentation;

import br.com.tqi.evolution.domain.Client;
import br.com.tqi.evolution.domain.Role;
import br.com.tqi.evolution.services.ClientService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

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
}

@Data
class RoleToClientDTO {
    private String email;
    private String roleName;
}

@Data
class RequestBorrowingDTO {
    private Double value;
    private Date firstInstallmentDate;
    private int numberOfInstallments;
}
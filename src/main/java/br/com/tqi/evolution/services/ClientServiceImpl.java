package br.com.tqi.evolution.services;

import br.com.tqi.evolution.domain.Borrow;
import br.com.tqi.evolution.domain.Client;
import br.com.tqi.evolution.domain.Role;
import br.com.tqi.evolution.repositories.BorrowRepository;
import br.com.tqi.evolution.repositories.RoleRepository;
import br.com.tqi.evolution.repositories.ClientRepository;
import br.com.tqi.evolution.usecases.RequestBorrowing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ClientServiceImpl implements ClientService, UserDetailsService {
    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final BorrowRepository borrowRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(email);
        if (client == null) {
            log.error("Client not found in database: {}", email);
            throw new UsernameNotFoundException("Client not found in database");
        }
        log.info("Client found in database: {}", email);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        client.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(client.getEmail(), client.getPassword(), authorities);
    }

    @Override
    public Client saveClient(Client client) {
        log.info("Saving new user {} to the database", client.getEmail());
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        Role role = roleRepository.findByName("ROLE_CLIENT");
        client.getRoles().add(role);
        return clientRepository.save(client);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToClient(String email, String roleName) {
        log.info("Adding a new role {} to user {}", roleName, email);
        Client client = clientRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        client.getRoles().add(role);
    }

    @Override
    public Client getClient(String email) {
        log.info("Getting user {}", email);
        return clientRepository.findByEmail(email);
    }

    @Override
    public Client getClientById(Long id) throws Exception {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            log.error("Client not found with id: {}", id);
            throw new Exception("Client not found");
        }
        return clientOptional.get();
    }

    @Override
    public List<Client> getClients() {
        log.info("Getting all users");
        return clientRepository.findAll();
    }

    @Override
    public void requestBorrowing(String email, Double value, ZonedDateTime firstInstallmentDate, int numberOfInstallments) throws Exception {
        log.info("Reuquest borrow to user: {}", email);
        RequestBorrowing requestBorrowing = new RequestBorrowing();
        Borrow borrow = requestBorrowing.execute(value, firstInstallmentDate, numberOfInstallments);
        String code = UUID.randomUUID().toString();
        borrow.setCode(code);
        borrowRepository.save(borrow);
        Client client = clientRepository.findByEmail(email);
        client.getBorrows().add(borrow);
    }

    @Override
    public Borrow getBorrow(Long borrowId) throws Exception {
        Optional<Borrow> borrowOptional = borrowRepository.findById(borrowId);
        if (borrowOptional.isEmpty()) {
            log.error("Error to get borrow to user: {}", borrowId);
            throw new Exception("Borrow not found");
        }
        return borrowOptional.get();
    }
}

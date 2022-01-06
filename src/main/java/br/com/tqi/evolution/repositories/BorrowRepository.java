package br.com.tqi.evolution.repositories;

import br.com.tqi.evolution.domain.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
}

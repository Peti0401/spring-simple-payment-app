package hu.gyarmatip.simplepaymentapp.repository;

import hu.gyarmatip.simplepaymentapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

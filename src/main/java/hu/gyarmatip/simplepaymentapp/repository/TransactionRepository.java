package hu.gyarmatip.simplepaymentapp.repository;

import hu.gyarmatip.simplepaymentapp.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}

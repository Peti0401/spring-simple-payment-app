package hu.gyarmatip.simplepaymentapp.repository;

import hu.gyarmatip.simplepaymentapp.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

}

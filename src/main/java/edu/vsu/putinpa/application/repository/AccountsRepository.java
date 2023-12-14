package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.orm.api.OrmRepository;
import edu.vsu.putinpa.infrastructure.orm.api.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface AccountsRepository extends OrmRepository<Account, UUID> {
    @Query("select * from account where id = ?;")
    Optional<Account> findByUUID(UUID uuid);

    @Query("select * from account where name = ?;")
    List<Account> findByName(String name);

    @Query("select * from account where creator_id = ?;")
    List<Account> findByCreator(Client creator);

    @Query("select * from account;")
    List<Account> findAll();
    Account save(Account account);
}

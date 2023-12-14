package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.orm.api.OrmRepository;
import edu.vsu.putinpa.infrastructure.orm.api.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface OperationsRepository extends OrmRepository<JournalOperation, UUID> {
    @Query("select * from journal_operation where id = ?;")
    Optional<JournalOperation> findByUUID(UUID uuid);

    @Query("select * from journal_operation where client_id = ?;")
    List<JournalOperation> findByClient(Client client);

    @Query("select * from journal_operation;")
    List<JournalOperation> findAll();
    JournalOperation save(JournalOperation journalOperation);
}

package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.JournalOperation;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface OperationsRepository {
    Optional<JournalOperation> findByUUID(UUID uuid);
    Collection<JournalOperation> findByClient(Client client);
    Collection<JournalOperation> findBySender(Account account);

    /**
     * Возвращает операции, в которых указанный счёт выступает как получатель.
     * Инами словами, выбирает все операции перевода, в которых участвовал указанный счёт.
     * @param recipient счёт, по которому осуществляется поиск
     * @return операции, в которых указанный счёт выступает как получатель
     */
    Collection<JournalOperation> findByRecipient(Account recipient);

    /**
     * Возвращает операции, в которых указанный счёт выступает либо как получатель, либо как отправитель.
     * Инами словами, выбирает все операции перевода, в которых участвовал указанный счёт.
     * @param account счёт, по которому осуществляется поиск
     * @return операции, в которых указанный счёт выступает либо как получатель, либо как отправитель.
     */
    Collection<JournalOperation> findBySenderOrRecipient(Account account);
    Collection<JournalOperation> findAll();
    JournalOperation save(JournalOperation journalOperation);
}

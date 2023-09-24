package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface OperationsRepository {
    JournalOperation save(JournalOperation journalOperation);
    Optional<JournalOperation> getByUUID(UUID uuid);
    Collection<JournalOperation> getByClient(Client client);
    Collection<JournalOperation> getBySender(Account account);

    /**
     * Возвращает операции, в которых указанный счёт выступает как получатель.
     * Инами словами, выбирает все операции перевода, в которых участвовал указанный счёт.
     * @param recipient счёт, по которому осуществляется поиск
     * @return операции, в которых указанный счёт выступает как получатель
     */
    Collection<JournalOperation> getByRecipient(Account recipient);

    /**
     * Возвращает операции, в которых указанный счёт выступает либо как получатель, либо как отправитель.
     * Инами словами, выбирает все операции перевода, в которых участвовал указанный счёт.
     * @param account счёт, по которому осуществляется поиск
     * @return операции, в которых указанный счёт выступает либо как получатель, либо как отправитель.
     */
    Collection<JournalOperation> getBySenderOrRecipient(Account account);
}

package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.service.Operation;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface OperationsRepository {
    Optional<Operation> getByUUID(UUID uuid);
    Collection<Operation> getByClient(Client client);
    Collection<Operation> getBySender(Account account);

    /**
     * Возвращает операции, в которых указанный счёт выступает как получатель.
     * Инами словами, выбирает все операции перевода, в которых участвовал указанный счёт.
     * @param recipient счёт, по которому осуществляется поиск
     * @return операции, в которых указанный счёт выступает как получатель
     */
    Collection<Operation> getByRecipient(Account recipient);

    /**
     * Возвращает операции, в которых указанный счёт выступает либо как получатель, либо как отправитель.
     * Инами словами, выбирает все операции перевода, в которых участвовал указанный счёт.
     * @param account счёт, по которому осуществляется поиск
     * @return операции, в которых указанный счёт выступает либо как получатель, либо как отправитель.
     */
    Collection<Operation> getBySenderOrRecipient(Account account);
}

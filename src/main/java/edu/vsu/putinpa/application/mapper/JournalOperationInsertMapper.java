package edu.vsu.putinpa.application.mapper;

import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.infrastructure.orm.InsertMappingInfo;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

import static edu.vsu.putinpa.infrastructure.util.sql.SqlUtil.instantToTimestamp;

public class JournalOperationInsertMapper implements Function<JournalOperation, InsertMappingInfo> {
    @Override
    public InsertMappingInfo apply(JournalOperation journalOperation) {
        String sql = """
                insert into journal_operation
                (id, whencreated, client_id, sender_id, recipient_id, currency_id, balance, type)
                values (?, ?, ?, ?, ?, ?, ?, ?)
                on duplicate key
                update whencreated=?, client_id=?, sender_id=?, recipient_id=?, currency_id=?, balance=?, type=?;""";

        Timestamp whenCreated = instantToTimestamp(journalOperation.getWhenCreated());
        List<Object> values = List.of(
                journalOperation.getUuid(), whenCreated, journalOperation.getClient().getUuid(), journalOperation.getSender().getUuid(),
                                            journalOperation.getRecipient().getUuid(), journalOperation.getMoney().currency(), journalOperation.getMoney().value(), journalOperation.getType(),
                                            whenCreated, journalOperation.getClient().getUuid(), journalOperation.getSender().getUuid(),
                                            journalOperation.getRecipient().getUuid(), journalOperation.getMoney().currency(), journalOperation.getMoney().value(), journalOperation.getType()
        );
        return new InsertMappingInfo(sql, values);
    }
}

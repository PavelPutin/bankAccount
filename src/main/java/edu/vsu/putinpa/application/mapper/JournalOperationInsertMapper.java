package edu.vsu.putinpa.application.mapper;

import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.infrastructure.orm.InsertMappingInfo;

import java.sql.Timestamp;
import java.util.ArrayList;
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
                on conflict (id) do update set
                whencreated=?, client_id=?, sender_id=?, recipient_id=?, currency_id=?, balance=?, type=?;""";

        Timestamp whenCreated = instantToTimestamp(journalOperation.getWhenCreated());
        List<Object> values = new ArrayList<>();
        values.add(journalOperation.getUuid());
        for (int i = 0; i < 2; i++) {
            values.add(whenCreated);
            values.add(journalOperation.getClient().getUuid());
            values.add(journalOperation.getSender() == null ? null : journalOperation.getSender().getUuid());
            values.add(journalOperation.getRecipient() == null ? null : journalOperation.getRecipient().getUuid());
            values.add(journalOperation.getMoney().currency());
            values.add(journalOperation.getMoney().value());
            values.add(journalOperation.getType().toString());
        }
        return new InsertMappingInfo(sql, values);
    }
}

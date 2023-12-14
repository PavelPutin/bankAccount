package edu.vsu.putinpa.application.mapper;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.infrastructure.orm.InsertMappingInfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static edu.vsu.putinpa.infrastructure.util.sql.SqlUtil.instantToTimestamp;

public class AccountInsertMapper implements Function<Account, InsertMappingInfo> {
    @Override
    public InsertMappingInfo apply(Account account) {
        String sql = """
                insert into account
                (id, name, whenopened, whenClosed, creator_id, balance, currency_id)
                values (?, ?, ?, ?, ?, ?, ?)
                on conflict (id) do update set
                name=?, whenopened=?, whenClosed=?, creator_id=?, balance=?, currency_id=?;""";

        Timestamp whenOpened = instantToTimestamp(account.getWhenOpened());
        Timestamp whenClosed = instantToTimestamp(account.getWhenClosed());
        List<Object> values = new ArrayList<>();
        values.add(account.getUuid());
        for (int i = 0; i < 2; i++) {
            values.add(account.getName());
            values.add(whenOpened);
            values.add(whenClosed);
            values.add(account.getCreator().getUuid());
            values.add(account.getBalance().value());
            values.add(account.getBalance().currency());
            values.add(account.getName());
        }
        return new InsertMappingInfo(sql, values);
    }
}

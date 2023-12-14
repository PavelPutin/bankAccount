package edu.vsu.putinpa.application.mapper;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.infrastructure.orm.InsertMappingInfo;

import java.sql.Timestamp;
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
                on duplicate key
                update name=?, whenopened=?, whenClosed=?, creator_id=?, balance=?, currency_id=?""";

        Timestamp whenOpened = instantToTimestamp(account.getWhenOpened());
        Timestamp whenClosed = instantToTimestamp(account.getWhenClosed());
        List<Object> values = List.of(
                account.getUuid(), account.getName(), whenOpened, whenClosed, account.getCreator().getUuid(), account.getBalance().value(), account.getBalance().currency(),
                                   account.getName(), whenOpened, whenClosed, account.getCreator().getUuid(), account.getBalance().value(), account.getBalance().currency()
        );
        return new InsertMappingInfo(sql, values);
    }
}

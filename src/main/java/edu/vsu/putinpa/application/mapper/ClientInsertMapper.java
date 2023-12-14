package edu.vsu.putinpa.application.mapper;

import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.infrastructure.orm.InsertMappingInfo;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

import static edu.vsu.putinpa.infrastructure.util.sql.SqlUtil.instantToTimestamp;

public class ClientInsertMapper implements Function<Client, InsertMappingInfo> {
    @Override
    public InsertMappingInfo apply(Client client) {
        String sql = """
                insert into client
                (id, name, password, whencreated)
                values (?, ?, ?, ?)
                on duplicate key
                update name=?, password=?, whencreated=?;""";

        Timestamp whenCreated = instantToTimestamp(client.getWhenCreated());
        List<Object> values = List.of(
                client.getUuid(), client.getName(), client.getPassword(), whenCreated,
                                  client.getName(), client.getPassword(), whenCreated
        );
        return new InsertMappingInfo(sql, values);
    }
}

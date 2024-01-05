package edu.vsu.putinpa.application.model;

import edu.vsu.putinpa.application.mapper.ClientInsertMapper;
import edu.vsu.putinpa.infrastructure.orm.api.Column;
import edu.vsu.putinpa.infrastructure.orm.api.InsertMappingBy;
import edu.vsu.putinpa.infrastructure.orm.api.Table;

import java.time.Instant;

@Table("client")
@InsertMappingBy(ClientInsertMapper.class)
public class Client extends EntityWithUUID {
    @Column("name")
    private String name;
    @Column("password")
    private String password;
    @Column("whencreated")
    private Instant whenCreated;

    {
        whenCreated = Instant.now();
    }

    public Client() {}

    public Client(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }
}

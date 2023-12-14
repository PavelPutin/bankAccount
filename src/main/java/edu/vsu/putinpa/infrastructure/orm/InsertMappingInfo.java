package edu.vsu.putinpa.infrastructure.orm;

import java.util.List;

public record InsertMappingInfo(
        String sql,
        List<Object> values
) {
}

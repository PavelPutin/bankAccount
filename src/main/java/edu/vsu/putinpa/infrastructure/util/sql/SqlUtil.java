package edu.vsu.putinpa.infrastructure.util.sql;

import java.sql.Timestamp;
import java.time.Instant;

public class SqlUtil {
    public static Timestamp instantToTimestamp(Instant instant) {
        return instant != null ? Timestamp.from(instant) : null;
    }
}

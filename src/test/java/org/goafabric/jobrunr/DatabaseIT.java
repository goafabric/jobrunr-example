package org.goafabric.jobrunr;

import org.jobrunr.storage.sql.common.DatabaseSqlMigrationFileProvider;
import org.junit.jupiter.api.Test;

public class DatabaseIT {
    @Test
    public void test() {
        DatabaseSqlMigrationFileProvider.main(new String[]{"postgres", "masterdata."});
    }
}

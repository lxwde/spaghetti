package com.zpmc.ztos.infra.base.common.configs;

import com.zpmc.ztos.infra.base.business.interfaces.IDetailedDiagnostics;
import com.zpmc.ztos.infra.base.common.model.PostgresCarinaDialect;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;

public class CustomizableConfiguration extends Configuration
        implements IDetailedDiagnostics {
    @Override
    public String getDetailedDiagnostics() {
        StringBuilder buf = new StringBuilder();
        try {
            buf.append(this.tableNameBinding + "\n\n");
            buf.append(this.typeDefs);
        }
        catch (Exception e) {
            buf.append(e);
        }
        return buf.toString();
    }

    public String[] generateDropSchemaScript(Dialect inDialect) throws HibernateException {
        String[] dropSQL = super.generateDropSchemaScript(inDialect);
        if (inDialect instanceof PostgresCarinaDialect) {
            for (int i = 0; i < dropSQL.length; ++i) {
                dropSQL[i] = dropSQL[i].replaceAll("drop\\s+table\\s+qrtz_job_details", "drop table qrtz_job_details cascade");
            }
        }
        return dropSQL;
    }

    public String[] generateSchemaCreationScript(Dialect inDialect) throws HibernateException {
        String[] createSQL = super.generateSchemaCreationScript(inDialect);
        if (inDialect instanceof PostgresCarinaDialect) {
            for (int i = 0; i < createSQL.length; ++i) {
                createSQL[i] = createSQL[i].replaceAll("bool\\s+default\\s+1", "bool default '1'");
                createSQL[i] = createSQL[i].replaceAll("bool\\s+default\\s+0", "bool default '0'");
            }
        }
        return createSQL;
    }

    public String toString() {
        return super.toString();
    }

}

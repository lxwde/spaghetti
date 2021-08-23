package com.zpmc.ztos.infra.base.common.model;

import org.hibernate.Hibernate;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.id.SequenceIdentityGenerator;
import org.hibernate.type.Type;

public class PostgresCarinaDialect extends PostgreSQLDialect {
    public PostgresCarinaDialect() {
        this.registerColumnType(2004, "bytea");
        this.registerColumnType(2003, "bytea");
        this.registerFunction("casttime", (SQLFunction)new SQLFunctionTemplate((Type) Hibernate.TIME, " TO_CHAR(?1, 'HH24:MI')"));
    }

    public boolean requiresCastingOfParametersInSelectClause() {
        return true;
    }

    public Class getNativeIdentifierGeneratorClass() {
        return SequenceIdentityGenerator.class;
    }
}

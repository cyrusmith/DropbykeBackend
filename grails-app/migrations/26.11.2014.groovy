databaseChangeLog = {

    changeSet(author: "cyrusmith (generated)", id: "1416977633461-1") {
        createTable(tableName: "account") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "accountPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "sum", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1416977633461-2") {
        createTable(tableName: "operation") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "operationPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "account_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "amount", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "sum_after", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "sum_before", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "class", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "charge_id", type: "varchar(255)")
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1416977633461-3") {
        dropForeignKeyConstraint(baseTableName: "charge", baseTableSchemaName: "dropbike", constraintName: "FK_9pfivyijy30fny2qnw1gta91c")
    }

    changeSet(author: "cyrusmith (generated)", id: "1416977633461-4") {
        dropForeignKeyConstraint(baseTableName: "charge", baseTableSchemaName: "dropbike", constraintName: "FK_rf4luejj74eh02aan6lsfh8a6")
    }

    changeSet(author: "cyrusmith (generated)", id: "1416977633461-7") {
        createIndex(indexName: "FK_h6dr47em6vg85yuwt4e2roca4", tableName: "account") {
            column(name: "user_id")
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1416977633461-8") {
        createIndex(indexName: "user_id_uniq_1416977633160", tableName: "account", unique: "true") {
            column(name: "user_id")
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1416977633461-9") {
        createIndex(indexName: "FK_cwjmw3oq5fhkceahr96k1xh3n", tableName: "operation") {
            column(name: "account_id")
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1416977633461-10") {
        dropTable(tableName: "charge")
    }

    changeSet(author: "cyrusmith (generated)", id: "1416977633461-5") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "account", constraintName: "FK_h6dr47em6vg85yuwt4e2roca4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }

    changeSet(author: "cyrusmith (generated)", id: "1416977633461-6") {
        addForeignKeyConstraint(baseColumnNames: "account_id", baseTableName: "operation", constraintName: "FK_cwjmw3oq5fhkceahr96k1xh3n", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "account", referencesUniqueColumn: "false")
    }
}

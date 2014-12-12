databaseChangeLog = {

    changeSet(author: "cyrusmith (generated)", id: "1416466714237-1") {
        createTable(tableName: "user_bike") {
            column(name: "bike_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "active", type: "bit") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1416466714237-2") {
        addPrimaryKey(columnNames: "bike_id, user_id", constraintName: "user_bikePK", tableName: "user_bike")
    }

    changeSet(author: "cyrusmith (generated)", id: "1416466714237-5") {
        createIndex(indexName: "FK_419pchy07fcmydj8tse3703qx", tableName: "user_bike") {
            column(name: "user_id")
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1416466714237-6") {
        createIndex(indexName: "FK_8mimudmor2jsb98s0yaqbg0cx", tableName: "user_bike") {
            column(name: "bike_id")
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1416466714237-3") {
        addForeignKeyConstraint(baseColumnNames: "bike_id", baseTableName: "user_bike", constraintName: "FK_8mimudmor2jsb98s0yaqbg0cx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "bike", referencesUniqueColumn: "false")
    }

    changeSet(author: "cyrusmith (generated)", id: "1416466714237-4") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_bike", constraintName: "FK_419pchy07fcmydj8tse3703qx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }


    changeSet(author: "cyrusmith (generated)", id: "1416838574018-1") {
        addColumn(tableName: "bike") {
            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1416838574018-2") {
        dropForeignKeyConstraint(baseTableName: "user_bike", baseTableSchemaName: "dropbike", constraintName: "FK_8mimudmor2jsb98s0yaqbg0cx")
    }

    changeSet(author: "cyrusmith (generated)", id: "1416838574018-3") {
        dropForeignKeyConstraint(baseTableName: "user_bike", baseTableSchemaName: "dropbike", constraintName: "FK_419pchy07fcmydj8tse3703qx")
    }

    changeSet(author: "cyrusmith (generated)", id: "1416838574018-5") {
        createIndex(indexName: "FK_h3jrbfm2ljuc12a1th1yuekhl", tableName: "bike") {
            column(name: "user_id")
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1416838574018-6") {
        dropTable(tableName: "user_bike")
    }

    changeSet(author: "cyrusmith (generated)", id: "1416838574018-4") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "bike", constraintName: "FK_h3jrbfm2ljuc12a1th1yuekhl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }

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

    changeSet(author: "cyrusmith (generated)", id: "1416978789970-1") {
        addColumn(tableName: "operation") {
            column(name: "created", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1417012869399-1") {
        addColumn(tableName: "operation") {
            column(name: "model", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1417012869399-2") {
        addColumn(tableName: "operation") {
            column(name: "model_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "cyrusmith (generated)", id: "1417062453626-1") {
        addColumn(tableName: "ride") {
            column(name: "sum_checkout", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

}

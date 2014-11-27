databaseChangeLog = {

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
}

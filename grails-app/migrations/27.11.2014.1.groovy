databaseChangeLog = {

    changeSet(author: "cyrusmith (generated)", id: "1417062453626-1") {
        addColumn(tableName: "ride") {
            column(name: "sum_checkout", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }
}

databaseChangeLog = {

    changeSet(author: "cyrusmith (generated)", id: "1416978789970-1") {
        addColumn(tableName: "operation") {
            column(name: "created", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }
}

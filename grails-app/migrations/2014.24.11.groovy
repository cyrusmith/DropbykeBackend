databaseChangeLog = {

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
}

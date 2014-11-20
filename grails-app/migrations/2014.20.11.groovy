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
}

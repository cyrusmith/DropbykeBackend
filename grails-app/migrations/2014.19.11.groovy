databaseChangeLog = {

	changeSet(author: "cyrusmith (generated)", id: "1416379167979-1") {
		createIndex(indexName: "FK_it77eq964jhfqtu54081ebtio", tableName: "user_role") {
			column(name: "role_id")
		}
	}
}

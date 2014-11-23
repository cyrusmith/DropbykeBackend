databaseChangeLog = {

	changeSet(author: "cyrusmith (generated)", id: "1416751068238-1") {
		dropColumn(columnName: "has_photo", tableName: "ride")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416751068238-2") {
		dropColumn(columnName: "lock_password", tableName: "ride")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416751068238-3") {
		dropColumn(columnName: "card_expire", tableName: "user")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416751068238-4") {
		dropColumn(columnName: "card_name", tableName: "user")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416751068238-5") {
		dropColumn(columnName: "card_number", tableName: "user")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416751068238-6") {
		dropColumn(columnName: "card_verified", tableName: "user")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416751068238-7") {
		dropColumn(columnName: "cardcvc", tableName: "user")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416751068238-8") {
		dropColumn(columnName: "stripe_customer_id", tableName: "user")
	}
}

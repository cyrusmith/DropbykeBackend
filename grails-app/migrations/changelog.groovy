databaseChangeLog = {

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-1") {
		createTable(tableName: "authentication_token") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "authenticatioPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "token_value", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-2") {
		createTable(tableName: "bike") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "bikePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "active", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "address", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_ride_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_user_phone", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "lat", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "lng", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "lock_password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "locked", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "message_from_last_user", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "price_rate", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "rating", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "sku", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-3") {
		createTable(tableName: "bike_rating") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "bike_ratingPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "bike_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "rating", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "ride_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-4") {
		createTable(tableName: "card") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "cardPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "cvc", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "expire", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "number", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "stripe_customer_id", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-5") {
		createTable(tableName: "charge") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "chargePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "amount", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "card_number", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "ride_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "stripe_charge_id", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "timestamp", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-6") {
		createTable(tableName: "path") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pathPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "ride_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-7") {
		createTable(tableName: "ride") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ridePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "bike_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "charged", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "complete", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "distance", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "message", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "start_address", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "start_lat", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "start_lng", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "start_time", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "stop_address", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "stop_lat", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "stop_lng", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "stop_time", type: "bigint") {
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

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-8") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-9") {
		createTable(tableName: "user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "edited_once", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "facebook_id", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "is_online", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "phone", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "share_facebook", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-10") {
		createTable(tableName: "user_role") {
			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-11") {
		addPrimaryKey(columnNames: "role_id, user_id", constraintName: "user_rolePK", tableName: "user_role")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-23") {
		createIndex(indexName: "sku_uniq_1416378990906", tableName: "bike", unique: "true") {
			column(name: "sku")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-24") {
		createIndex(indexName: "FK_6mrke3a0kbaxjte756fhm92sn", tableName: "bike_rating") {
			column(name: "user_id")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-25") {
		createIndex(indexName: "FK_dsvbu4oumu91oxeuhyqk7bbm5", tableName: "bike_rating") {
			column(name: "ride_id")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-26") {
		createIndex(indexName: "FK_vqw20n58bscajx7hs67dpvk7", tableName: "bike_rating") {
			column(name: "bike_id")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-27") {
		createIndex(indexName: "FK_bghvg4xo76su71a9k40s0rplq", tableName: "card") {
			column(name: "user_id")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-28") {
		createIndex(indexName: "FK_9pfivyijy30fny2qnw1gta91c", tableName: "charge") {
			column(name: "ride_id")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-29") {
		createIndex(indexName: "FK_rf4luejj74eh02aan6lsfh8a6", tableName: "charge") {
			column(name: "user_id")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-30") {
		createIndex(indexName: "FK_h7ptnjsr8ltpe5n7xdcobnyg3", tableName: "path") {
			column(name: "ride_id")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-31") {
		createIndex(indexName: "FK_6monxh6jdvoxv45pepuj2xu2f", tableName: "ride") {
			column(name: "bike_id")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-32") {
		createIndex(indexName: "FK_t1j9pjna87g6lq08ng7w96d4j", tableName: "ride") {
			column(name: "user_id")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-33") {
		createIndex(indexName: "authority_uniq_1416378990926", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-34") {
		createIndex(indexName: "username_uniq_1416378990936", tableName: "user", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-35") {
		createIndex(indexName: "FK_apcc8lxk2xnug8377fatvbn04", tableName: "user_role") {
			column(name: "user_id")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-36") {
		createIndex(indexName: "FK_it77eq964jhfqtu54081ebtio", tableName: "user_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-12") {
		addForeignKeyConstraint(baseColumnNames: "bike_id", baseTableName: "bike_rating", constraintName: "FK_vqw20n58bscajx7hs67dpvk7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "bike", referencesUniqueColumn: "false")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-13") {
		addForeignKeyConstraint(baseColumnNames: "ride_id", baseTableName: "bike_rating", constraintName: "FK_dsvbu4oumu91oxeuhyqk7bbm5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "ride", referencesUniqueColumn: "false")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-14") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "bike_rating", constraintName: "FK_6mrke3a0kbaxjte756fhm92sn", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-15") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "card", constraintName: "FK_bghvg4xo76su71a9k40s0rplq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-16") {
		addForeignKeyConstraint(baseColumnNames: "ride_id", baseTableName: "charge", constraintName: "FK_9pfivyijy30fny2qnw1gta91c", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "ride", referencesUniqueColumn: "false")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-17") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "charge", constraintName: "FK_rf4luejj74eh02aan6lsfh8a6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-18") {
		addForeignKeyConstraint(baseColumnNames: "ride_id", baseTableName: "path", constraintName: "FK_h7ptnjsr8ltpe5n7xdcobnyg3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "ride", referencesUniqueColumn: "false")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-19") {
		addForeignKeyConstraint(baseColumnNames: "bike_id", baseTableName: "ride", constraintName: "FK_6monxh6jdvoxv45pepuj2xu2f", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "bike", referencesUniqueColumn: "false")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-20") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "ride", constraintName: "FK_t1j9pjna87g6lq08ng7w96d4j", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-21") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK_it77eq964jhfqtu54081ebtio", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "cyrusmith (generated)", id: "1416378991016-22") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK_apcc8lxk2xnug8377fatvbn04", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}


	include file: '2014.20.11.groovy'



	include file: '2014.24.11.groovy'
}

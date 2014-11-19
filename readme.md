//===========================DATA MIGRATION============================
//Run changelog.groovy during application deployment on server?
grails.plugin.databasemigration.updateOnStart = true
//File used to run the db migration scripts
grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']
//Absolute path of changelog.groovy in the app base dir
grails.plugin.databasemigration.changelogLocation = 'migrations'
//  the default schema to use when running auto-migrate on start
//grails.plugin.databasemigration. updateOnStartDefaultSchema ='schema' // You may not need this in MYSQL
//=====================================================================
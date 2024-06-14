package de.tungbeier.panache

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class PostgresResource : QuarkusTestResourceLifecycleManager {

    private val databaseName = "mydatabase"
    private val databaseUser = "sarah"
    private val databasePassword = "connor"

    private val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
        .withDatabaseName(databaseName)
        .withUsername(databaseUser)
        .withPassword(databasePassword)

    override fun start(): Map<String, String> {
        postgres.start()

        return mapOf(
            "quarkus.datasource.username" to databaseUser,
            "quarkus.datasource.password" to databasePassword,
            "quarkus.datasource.reactive.url" to postgres.jdbcUrl.replace("jdbc", "vertx-reactive"),
        )
    }

    override fun stop() {
        postgres.stop()
    }
}

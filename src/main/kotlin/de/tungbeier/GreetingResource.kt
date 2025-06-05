package de.tungbeier

import de.tungbeier.config.Ruleset
import io.quarkus.logging.Log
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.event.Observes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/hello")
class GreetingResource(val ruleset: Ruleset) {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Hello from Quarkus REST"

    fun startup(@Observes ev: StartupEvent) {
        val logMsg = buildString {
            ruleset.enabledEventsPerChannel().forEach { (country, eventToChannels) ->
                appendLine("----- $country -----")
                eventToChannels.forEach { typeAndChannel ->
                    appendLine("- ${typeAndChannel.event()}: ${typeAndChannel.channels()}")
                }
            }
        }

        Log.info("Quarkus started on ${ev::class.simpleName} with the following ruleset:\n$logMsg")
    }
}
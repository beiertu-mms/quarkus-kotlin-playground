package de.tungbeier.config

import io.smallrye.config.ConfigMapping
import io.smallrye.config.WithName

@ConfigMapping(prefix = "ruleset.customer-order")
interface Ruleset {

    @WithName("event-and-channels")
    fun enabledEventsPerChannel(): Map<String, Set<TypeAndChannel>>

    interface TypeAndChannel {
        fun event(): String

        fun channels(): Set<String>
    }
}
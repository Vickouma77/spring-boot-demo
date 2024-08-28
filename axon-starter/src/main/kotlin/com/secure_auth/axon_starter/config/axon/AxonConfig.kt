package com.secure_auth.axon_starter.config.axon

import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration
import org.springframework.context.annotation.Configuration


@Configuration
class AxonConfig {

    fun configure(config: EventProcessingConfigurer) {
        config.registerTrackingEventProcessorConfiguration {
            TrackingEventProcessorConfiguration.forSingleThreadedProcessing()
        }
    }
}
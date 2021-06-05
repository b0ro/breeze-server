package org.boro.breezeserver.infrastructure

import org.boro.breezeserver.adapter.jpa.JpaPeakFlowRepository
import org.boro.breezeserver.adapter.jpa.SpringDataPeakFlowRepository
import org.boro.breezeserver.domain.PeakFlowFacade
import org.boro.breezeserver.domain.PeakFlowRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PeakFlowConfiguration {

    @Bean
    fun peakFlowRepository(repository: SpringDataPeakFlowRepository): PeakFlowRepository = JpaPeakFlowRepository(repository)

    @Bean
    fun peakFlowFacade(repository: PeakFlowRepository) = PeakFlowFacade(repository)
}

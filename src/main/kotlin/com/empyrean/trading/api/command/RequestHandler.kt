package com.empyrean.trading.api.command

import com.empyrean.trading.api.domain.DomainEvent
import com.empyrean.trading.api.domain.TradeClosedEvent
import com.empyrean.trading.api.domain.TradeEscalatedEvent
import com.empyrean.trading.api.query.QueryService
import com.empyrean.trading.api.query.Trade
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class TradeEventHandler(val queryService: QueryService, val kafkaTemplate: KafkaTemplate<String, DomainEvent<Trade>>) {
    val kafkaTopic: String = "trade-events"

    fun close(tradeId: UUID, user: String) {
        val trade = queryService.getTrade(tradeId)!!
        val tradeClosed = TradeClosedEvent(user, trade)
        val producerRecord = ProducerRecord<String, DomainEvent<Trade>>(kafkaTopic, tradeId.toString(), tradeClosed)
        kafkaTemplate.send(producerRecord)
    }

    fun escalate(tradeId: UUID, user: String) {
        val trade = queryService.getTrade(tradeId)!!
        val tradeEscalated = TradeEscalatedEvent(user, trade)
        val producerRecord = ProducerRecord<String, DomainEvent<Trade>>(kafkaTopic, tradeId.toString(), tradeEscalated)
        kafkaTemplate.send(producerRecord)
    }
}
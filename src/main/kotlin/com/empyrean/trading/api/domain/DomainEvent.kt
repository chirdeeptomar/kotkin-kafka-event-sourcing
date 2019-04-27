package com.empyrean.trading.api.domain

import com.empyrean.trading.api.query.Trade
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*


enum class EventType {
    TRADE_CLOSED,
    TRADE_ESCALATED,
    COMMENT_CREATED,
    COMMENT_DELETED
}

abstract class DomainEvent<T>(val id: UUID = UUID.randomUUID(),
                              val timestamp: LocalDateTime = LocalDateTime.now()) : Serializable {

    abstract var user: String
    abstract var subject: T
    abstract var eventType: EventType
}

data class TradeClosedEvent(
        override var user: String,
        override var subject: Trade,
        override var eventType: EventType = EventType.TRADE_CLOSED
) : DomainEvent<Trade>()

data class TradeEscalatedEvent(
        override var user: String,
        override var subject: Trade,
        override var eventType: EventType = EventType.TRADE_ESCALATED
) : DomainEvent<Trade>()
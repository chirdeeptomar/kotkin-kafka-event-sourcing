package com.empyrean.trading.api.stream

import com.empyrean.trading.api.domain.DomainEvent
import com.empyrean.trading.api.query.Trade
import org.apache.kafka.streams.StreamsBuilder
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Stream


@Component
class EventStream<T> {
    val kafkaTopic: String = "trade-events"

    fun getEventStream(id: UUID): Stream<DomainEvent<T>> {
        val stream = StreamsBuilder().stream<String, DomainEvent<Trade>>(kafkaTopic)
        val kstream = stream.filter { key, value -> key == id.toString() }
        return Stream.empty()
    }
}
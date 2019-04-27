package com.empyrean.trading.api.controllers

import com.empyrean.trading.api.command.TradeEventHandler
import com.empyrean.trading.api.domain.DomainEvent
import com.empyrean.trading.api.query.QueryService
import com.empyrean.trading.api.query.Trade
import com.empyrean.trading.api.stream.EventStream
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Stream

@RestController
@RequestMapping("/v1")
class TradeController(
        val queryService: QueryService,
        val tradeEventHandler: TradeEventHandler,
        val eventStream: EventStream<Trade>
) {
    @GetMapping("/api/trades")
    fun trades(): List<Trade> = queryService.getTrades()

    @GetMapping("/api/trades/{id}")
    fun trade(@PathVariable id: UUID): Trade? = queryService.getTrade(id)

    @PostMapping("/api/trades/{id}/close")
    fun close(@PathVariable id: UUID) {
        tradeEventHandler.close(id, "tomachi")
    }

    @PostMapping("/api/trades/{id}/escalate")
    fun escalate(@PathVariable id: UUID) {
        tradeEventHandler.escalate(id, "edward")
    } 

    @GetMapping("/api/trades/{id}/stream")
    fun stream(@PathVariable id: UUID): Stream<DomainEvent<Trade>> {
        return eventStream.getEventStream(id)
    }
}

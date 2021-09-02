package net.accelf.contral.core.timelines

typealias CreateTimelineHandler = (Map<String, String>) -> TimelineHandler
typealias TimelineHandlers = Map<String, CreateTimelineHandler>

class TimelineHandlersBuilder {

    private val handlers = mutableMapOf<String, CreateTimelineHandler>()

    fun register(name: String, handler: CreateTimelineHandler) {
        if (name in handlers) {
            throw HandlerExistsException(name)
        }

        handlers[name] = handler
    }

    operator fun plusAssign(other: TimelineHandlers) {
        other.forEach { (name, handler) -> register(name, handler) }
    }

    fun build(): TimelineHandlers = handlers

    class HandlerExistsException(name: String) :
        IllegalArgumentException("Handler $name already registered")
}

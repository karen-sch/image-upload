package ren.iamka

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ren.iamka.plugins.*

fun main() {
    embeddedServer(Netty, port = 9090, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
}

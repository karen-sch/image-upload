package ren.iamka.plugins

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World 1234!")
        }

        post("/upload") {
            val multipartData = call.receiveMultipart()

            var filename = ""

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        filename = part.originalFileName as String
                        val fileBytes = part.streamProvider().readBytes()
                        File("uploads/$filename").writeBytes(fileBytes)
                    }

                    else -> {}
                }
                part.dispose()
            }
            call.respondText(filename)
        }

        get("/image/{filename}") {
            val filename = call.parameters["filename"]

            call.respondBytes(
                contentType = ContentType.parse("image/jpeg"),
                provider = { File("uploads/$filename").readBytes() })
        }

    }
}


package mbjava

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

class MbUtils {
    companion object {
        private val mapper = jacksonObjectMapper()

        fun benchmarksJson(): String {
            val files = Files.walk(java.io.File(".").toPath())
                .filter(Files::isRegularFile)
                .map { it.toFile() }
                .filter { it.name.endsWith("Benchmark.java") }
                .filter { !it.name.startsWith("Abstract") }
                .map { it.nameWithoutExtension }
                .sorted()
                .toList()
            return mapper.writeValueAsString(files);
        }
    }
}
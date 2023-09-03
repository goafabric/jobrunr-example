package org.goafabric.jobrunr

import org.goafabric.jobrunr.Application.ApplicationRuntimeHints
import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.ExitCodeGenerator
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ImportRuntimeHints
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by amautsch on 26.06.2015.
 */
@SpringBootApplication
@ImportRuntimeHints(ApplicationRuntimeHints::class)
class Application {
    @Bean
    fun init(context: ApplicationContext?): CommandLineRunner {
        return CommandLineRunner { args: Array<String> ->
            if (args.size > 0 && "-check-integrity" == args[0]) {
                SpringApplication.exit(context, ExitCodeGenerator { 0 })
            }
        }
    }

    internal class ApplicationRuntimeHints : RuntimeHintsRegistrar {
        override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
            hints.resources().registerPattern("catalogdata/*.csv")
            hints.reflection()
                .registerType(CopyOnWriteArrayList::class.java, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS)
        }
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}

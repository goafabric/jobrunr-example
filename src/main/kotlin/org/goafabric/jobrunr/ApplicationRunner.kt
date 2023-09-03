package org.goafabric.jobrunr

import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.ExitCodeGenerator
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportRuntimeHints
import java.util.concurrent.CopyOnWriteArrayList


@Configuration
@ImportRuntimeHints(ApplicationRunner.ApplicationRuntimeHints::class)
class ApplicationRunner {
    @Bean
    fun runner(context: ApplicationContext?): CommandLineRunner? {
        return CommandLineRunner { args: Array<String> ->
            if (args.isNotEmpty() && "-check-integrity" == args[0]) {
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

}
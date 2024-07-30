
package edu.vu

import Dim1
import Dim2
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.Exec

import org.gradle.kotlin.dsl.*

/**
 * A simple 'hello world' plugin.
 */
class SamplePlugin: Plugin<Project> {

    override fun apply(project: Project): Unit = project.run  {
        val extension = project.extensions.create<SampleExtension>("sample")
//        logger.quiet("extension: \n directories: \n   proto: {}\n   gsl: {}",

        with (extension as ExtensionAware) {
            // https://docs.gradle.org/current/userguide/collections.html
            val dim1Container: NamedDomainObjectContainer<Dim1> =
                project.objects.domainObjectContainer(Dim1::class)
            project.extensions.add("dim1", dim1Container)
            logger.quiet("dim1 container size: {}", dim1Container.size)

            val dim2Container: NamedDomainObjectContainer<Dim2> =
                project.objects.domainObjectContainer(Dim2::class)
            project.extensions.add("dim2", dim2Container)
            logger.quiet("dim2 container size: {}", dim2Container.size)

            // Register a task
            tasks {
                val taskScope = this
                logger.quiet("tasks context: container size dim1: {},  dim2: {}", dim1Container.size, dim2Container.size)
                register("greeting") {
                    logger.quiet("greeting context: container size dim1: {},  dim2: {}", dim1Container.size, dim2Container.size)
                    dim1Container.all { dim1 ->
                        dim2Container.all { dim2 ->
                            logger.quiet("greeting context: container name dim1: {}, dim2: {}", dim1.name, dim2.name)
                            true
                        }
                    }
                    this.doLast {
                        println("Hello from plugin 'edu.vu.sample-plugin'")
                    }
                }
                dim1Container.all { dim1 ->
                    dim2Container.all { dim2 ->
                        logger.quiet("tasks context: container name dim1: {}, dim2: {}", dim1.name, dim2.name)
                        taskScope.register("task${dim1.name}${dim2.name}") {
                            logger.quiet("tasks context: container name dim1: {}, dim2: {}", dim1.name, dim2.name)
                            this.doLast {
                                println("Hello from plugin 'edu.vu.sample-plugin'")
                            }
                        }
                        true
                    }
                }
            }
        }
    }
}

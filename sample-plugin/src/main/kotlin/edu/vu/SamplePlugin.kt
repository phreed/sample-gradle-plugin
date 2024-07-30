
package edu.vu

import Dim1
import Dim2
import org.gradle.api.Action
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

        val srcDir = extension.srcDir
        logger.quiet("apply context: src dir: {}", srcDir)

        extension as ExtensionAware
        // https://docs.gradle.org/current/userguide/collections.html
        val dim1Container: NamedDomainObjectContainer<Dim1> =
            project.objects.domainObjectContainer(Dim1::class)
        project.extensions.add("dim1", dim1Container)
        logger.quiet("apply context: container size dim1: {}", dim1Container.size)

        val dim2Container: NamedDomainObjectContainer<Dim2> =
            project.objects.domainObjectContainer(Dim2::class)
        project.extensions.add("dim2", dim2Container)
        logger.quiet("apply context: container size dim2: {}", dim2Container.size)

        // Register a task
        tasks {
            logger.quiet("tasks context: src dir: {}", srcDir)

            logger.quiet("tasks context: container size dim1: {},  dim2: {}", dim1Container.size, dim2Container.size)
            register("greeting") {
                logger.quiet("greeting context: src dir: {}", srcDir)
                logger.quiet(
                    "greeting context: container size dim1: {},  dim2: {}",
                    dim1Container.size,
                    dim2Container.size
                )
                dim1Container.configureEach { val dim1 = this
                    dim2Container.configureEach { val dim2 = this
                        logger.quiet("greeting context: container name dim1: {}, dim2: {}", dim1.name, dim2.name)
                    }
                }
                this.doLast {
                    logger.quiet("Greetings from plugin 'edu.vu.sample-plugin' {}", this.name)
                }
            }
        }
        dim1Container.configureEach { val dim1 = this
            dim2Container.configureEach { val dim2 = this
                logger.quiet("apply All context: container name dim1: {}, dim2: {}", dim1.name, dim2.name)
                tasks.register("taskAll${dim1.name}${dim2.name}") {
                    logger.quiet("task: {}", this.name)
                    this.doLast {
                        logger.quiet("Greetings from plugin 'edu.vu.sample-plugin' {}",
                            this.name, dim1.x.orNull, dim1.y.orNull, dim2.z.orNull)
                    }
                }
            }
        }
    }
}

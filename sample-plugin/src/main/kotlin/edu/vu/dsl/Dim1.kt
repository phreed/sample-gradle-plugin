import org.gradle.api.Named
import org.gradle.api.provider.Property

abstract class Dim1: Named {
    abstract val x: Property<Int>
    abstract val y: Property<String>
}

import org.gradle.api.Named
import org.gradle.api.provider.Property

abstract class Dim2: Named {
    abstract val z: Property<Int>
}

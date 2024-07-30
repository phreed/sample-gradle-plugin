
// tag::plugin-use[]
plugins {
    id("edu.vu.sample-plugin")
}
// end::plugin-use[]

// tag::plugin-dsl[]
sample {
    srcDir = layout.projectDirectory
    srcDir = layout.buildDirectory.dir("sample")
    dim1 {
        create("G") {
            x = 1
            y = "dog"
        }
        create("H") {
            x = 2
            y = "cat"
        }
    }
    dim2 {
        create("X") {
            z = 1
        }
        create("Y") {
            z = 2
        }
    }
}
// end::plugin-dsl[]

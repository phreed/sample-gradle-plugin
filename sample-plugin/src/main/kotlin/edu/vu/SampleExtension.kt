package edu.vu

import Dim1
import Dim2
import org.gradle.api.Named
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.OutputDirectory

abstract class SampleExtension {

    @get:InputDirectory
    abstract val srcDir: DirectoryProperty
    @get:OutputDirectory
    abstract val tgtDir: DirectoryProperty

    @get:Nested
    abstract val dim1: List<Dim1>

    @get:Nested
    abstract val dim2: List<Dim2>
}
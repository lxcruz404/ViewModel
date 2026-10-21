// build.gradle.kts (raíz): archivo principal de Gradle.
// Aquí solo se declaran los plugins que usará el proyecto.
// "apply false" significa: "decláralo aquí, pero actívalo en el módulo app".
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

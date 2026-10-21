// settings.gradle.kts: configuración general del proyecto.
// Le dice a Gradle (la herramienta que compila la app) dónde buscar
// los plugins y las librerías, y qué módulos forman el proyecto.

// Lugares de internet donde Gradle busca los plugins.
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// Lugares de internet donde Gradle busca las librerías (dependencias).
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

// Nombre del proyecto.
rootProject.name = "ViewModelFacil"

// El proyecto tiene un solo módulo: la carpeta "app".
include(":app")

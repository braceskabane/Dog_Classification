pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
//        jcenter()
//        maven { url= uri("https://jitpack.io") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx/kotlinx/kotlinx-symbol-processing-api/") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
//        jcenter()
//        maven { url= uri("https://jitpack.io") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx/kotlinx/kotlinx-symbol-processing-api/") }
    }
}

rootProject.name = "Asclepius"
include(":app")
 
plugins {
    id("com.android.application") version "9.2.1" apply false
    id("com.android.library") version "8.11.1" apply false
    id("org.jetbrains.kotlin.android") version "2.2.0" apply false
    id("org.jetbrains.kotlin.jvm") version "2.2.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.0" apply false
}

subprojects {
    tasks.withType<Test>().configureEach { useJUnitPlatform() }
}

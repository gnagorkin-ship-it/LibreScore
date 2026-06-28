plugins { id("org.jetbrains.kotlin.jvm") }
kotlin { jvmToolchain(21) }
dependencies { implementation(project(":common")); implementation(project(":engine")); testImplementation("org.junit.jupiter:junit-jupiter:5.13.3"); testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.3") }

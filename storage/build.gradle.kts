plugins { id("com.android.library"); id("org.jetbrains.kotlin.android") }

android { namespace = "org.librescore.storage"; compileSdk = 36
    defaultConfig { minSdk = 23; testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" }
}
dependencies { implementation(project(":common")); implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2"); testImplementation("org.junit.jupiter:junit-jupiter:5.13.3") }

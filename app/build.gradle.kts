plugins { id("com.android.application"); id("org.jetbrains.kotlin.android"); id("org.jetbrains.kotlin.plugin.compose") }

android { namespace = "org.librescore.app"; compileSdk = 36
    defaultConfig { applicationId = "org.librescore.app"; minSdk = 23; targetSdk = 36; versionCode = 1; versionName = "0.1.0" }
    signingConfigs { create("release") {
        val path = providers.environmentVariable("RELEASE_KEYSTORE_PATH").orNull
        if (path != null) { storeFile = file(path); storePassword = System.getenv("KEYSTORE_PASSWORD"); keyAlias = System.getenv("KEY_ALIAS"); keyPassword = System.getenv("KEY_PASSWORD") }
    } }
    buildTypes { release { isMinifyEnabled = false; signingConfig = signingConfigs.getByName("release") } }
}

dependencies {
    implementation(project(":common")); implementation(project(":engine")); implementation(project(":pdf")); implementation(project(":musicxml")); implementation(project(":midi")); implementation(project(":player")); implementation(project(":storage"))
    implementation(platform("androidx.compose:compose-bom:2026.06.00")); implementation("androidx.activity:activity-compose:1.11.0"); implementation("androidx.compose.material3:material3"); implementation("androidx.compose.ui:ui"); implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0"); implementation("androidx.navigation:navigation-compose:2.9.1"); implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
}

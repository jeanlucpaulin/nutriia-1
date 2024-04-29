plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.nutriia1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nutriia1"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation ("com.google.code.gson:gson:2.8.8") // Vérifiez la dernière version de Gson sur le site Maven
    implementation ("androidx.cardview:cardview:1.0.0'")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //   implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation ("androidx.appcompat:appcompat:1.2.0")  // Exemple de dépendance
    implementation ("com.google.android.material:material:1.3.0")  // Exemple de dépendance
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")  // Exemple de dépendance
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")  // Exemple de dépendance

    dependencies {
        // Autres dépendances
        implementation ("com.squareup.okhttp3:okhttp:4.9.1") // Vérifiez la version
    }

    // Ajoutez d'autres dépendances au besoin
}


plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	id("kotlin-android")
	id("kotlin-kapt")
}

android {
	namespace = "ru.nsu.dependencyinjectionpractice"
	compileSdk = 34

	defaultConfig {
		applicationId = "ru.nsu.dependencyinjectionpractice"
		minSdk = 26
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	buildFeatures {
		viewBinding = true
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

val daggerVersion = "2.46.1"
val dagger = "com.google.dagger:dagger:$daggerVersion"
val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	testImplementation(libs.junit)
	implementation(libs.androidx.activity.ktx)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)

	kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.6.0")
	implementation(dagger)
	kapt(daggerCompiler)

}
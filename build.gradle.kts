buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.android.tools.build:gradle:7.1.3")
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}
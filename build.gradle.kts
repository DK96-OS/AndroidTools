buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("com.android.tools.build:gradle:4.2.1")
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

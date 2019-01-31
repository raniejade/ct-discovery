buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath("com.github.raniejade:gradle-plugin:0.1.0")
    }
}

plugins {
    kotlin("jvm") version "1.3.20"
}

apply(plugin = "com.github.raniejade.ctd-plugin")

allprojects {
    repositories {
        mavenLocal()
        jcenter()
    }
}
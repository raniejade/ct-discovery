plugins {
    kotlin("jvm") version "1.3.20" apply false
}

group = "com.github.raniejade"
version = "0.1.0"

allprojects {
    group = rootProject.group
    version = rootProject.version
    repositories {
        jcenter()
    }
}
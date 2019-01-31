plugins {
    `java-gradle-plugin`
    kotlin("jvm")
    kotlin("kapt")
}

gradlePlugin {
    plugins {
        create("ctdPlugin") {
            id = "com.github.raniejade.ctd-plugin"
            implementationClass = "com.github.raniejade.ct.CtdPlugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin-api"))

    implementation("com.google.auto.service:auto-service:1.0-rc4")
    kapt("com.google.auto.service:auto-service:1.0-rc4")
}
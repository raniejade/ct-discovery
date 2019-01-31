plugins {
    `maven-publish`
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("compiler-embeddable"))

    implementation("com.google.auto.service:auto-service:1.0-rc4")
    kapt("com.google.auto.service:auto-service:1.0-rc4")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.raniejade"
            artifactId = "ct-discovery"
            version = "0.1.0"

            from(components["java"])
        }
    }
}
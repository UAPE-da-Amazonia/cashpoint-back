plugins {
    `java-test-fixtures`
}

dependencies {
    implementation(libs.jakarta.persistence.api)
    implementation(libs.spring.boot.starter.data.jpa)
    testFixturesImplementation(libs.jakarta.persistence.api)
}

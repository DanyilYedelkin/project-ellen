plugins {
    java
    application
    pmd
}

group = "sk.tuke.kpi.oop"
version = "1.0"

val gamelibVersion = "2.6.1"

val backend = if (System.getProperty("os.name").contains("mac", ignoreCase = true)) "lwjgl2" else "lwjgl"

repositories {
    mavenCentral()
    maven(url=uri("https://repo.kpi.fei.tuke.sk/repository/maven-public"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

application {
    //mainClassName = "sk.tuke.kpi.gamelib.framework.Main"
    mainClassName = "sk.tuke.kpi.oop.game.Main"
}

dependencies {
    implementation("sk.tuke.kpi.gamelib:gamelib-framework:$gamelibVersion")
    implementation("sk.tuke.kpi.gamelib:gamelib-backend-$backend:$gamelibVersion")
    implementation("sk.tuke.kpi.gamelib:gamelib-inspector:$gamelibVersion")
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.plusAssign("-parameters")
    }
    pmdMain {
        classpath = sourceSets.main.get().runtimeClasspath
    }
}


configure<PmdExtension> {
    toolVersion = "6.18.0"
    isConsoleOutput = true
    ruleSets = emptyList()
    ruleSetFiles = files("src/main/resources/pmd-ruleset.xml")
}

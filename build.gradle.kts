import com.matthewprenger.cursegradle.CurseProject
import com.matthewprenger.cursegradle.CurseRelation
import com.matthewprenger.cursegradle.Options
import com.palantir.gradle.gitversion.VersionDetails
import net.fabricmc.loom.task.RemapJar
import net.fabricmc.loom.task.RemapSourcesJar

val minecraftVersion: String by project
val yarnMappings: String by project
val loaderVersion: String by project

val curseProjectId: String by project
val curseMinecraftVersion: String by project
val modJarBaseName: String by project
val modMavenGroup: String by project

val fabricVersion: String by project

plugins {
    java
    idea
    `maven-publish`
    id("fabric-loom") version "0.2.1-SNAPSHOT"
    id("com.palantir.git-version") version "0.11.0"
    id("com.matthewprenger.cursegradle") version "1.2.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

base {
    archivesBaseName = modJarBaseName
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "http://maven.fabricmc.net")
    maven(url = "https://minecraft.curseforge.com/api/maven")
}

val gitVersion: groovy.lang.Closure<Any> by extra
val versionDetails: groovy.lang.Closure<VersionDetails> by extra

version = "${gitVersion()}+mc$minecraftVersion"
group = modMavenGroup

minecraft {
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$minecraftVersion+$yarnMappings")
    modCompile("net.fabricmc:fabric-loader:$loaderVersion")

    modCompile("net.fabricmc.fabric-api:fabric-api-base:$fabricVersion")
    include("net.fabricmc.fabric-api:fabric-api-base:$fabricVersion")

    modCompile("net.fabricmc.fabric-api:fabric-resource-loader:$fabricVersion")
    include("net.fabricmc.fabric-api:fabric-resource-loader:$fabricVersion")
}

val processResources = tasks.getByName<ProcessResources>("processResources") {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        filter { line -> line.replace("%VERSION%", "${project.version}") }
    }
}

val javaCompile = tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val jar = tasks.getByName<Jar>("jar") {
    from("LICENSE")
}

val remapJar = tasks.getByName<RemapJar>("remapJar")

if (versionDetails().isCleanTag) {

    curseforge {
        if (project.hasProperty("curseforge_api_key")) {
            apiKey = project.property("curseforge_api_key")!!
        }

        project(closureOf<CurseProject> {
            id = curseProjectId
            changelog = file("changelog.txt")
            releaseType = "release"
            addGameVersion(curseMinecraftVersion)
            relations(closureOf<CurseRelation>{
                embeddedLibrary("fabric")
            })
        })

        options(closureOf<Options> {
            forgeGradleIntegration = false
        })
    }

    afterEvaluate {
        tasks.getByName("curseforge$curseProjectId").dependsOn(remapJar)
    }

}

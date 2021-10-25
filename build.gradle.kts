import com.palantir.gradle.gitversion.VersionDetails

val minecraftVersion: String by project
val parchmentVersion: String by project
val loaderVersion: String by project
val modId: String by project
val mavenGroup: String by project
val fabricVersion: String by project
val modMenuVersion: String by project

plugins {
  java
  `maven-publish`
  id("fabric-loom") version "0.10.42"
  id("com.palantir.git-version") version "0.12.3"
  id("com.diffplug.spotless") version "5.17.0"
  id("org.jetbrains.changelog") version "1.3.1"
}

val gitVersion: groovy.lang.Closure<Any> by extra
val versionDetails: groovy.lang.Closure<VersionDetails> by extra

version = "${gitVersion()}+mc$minecraftVersion"
group = mavenGroup

sourceSets {
  create("testmod") {
    compileClasspath += main.get().compileClasspath
    runtimeClasspath += main.get().runtimeClasspath
  }
}

loom {
  runs {
    create("testmodClient") {
      client()
      name("Testmod Client")
      ideConfigGenerated(true)
      source(sourceSets.getByName("testmod"))
    }
    create("gametest") {
      server()
      name("Game Test")
      ideConfigGenerated(true)
      source(sourceSets.getByName("testmod"))
      vmArg("-Dfabric-api.gametest")
      runDir("build/gametest")
    }
  }
}

tasks.getByName("test").dependsOn("runGametest")

repositories {
  // Loom adds the essential maven repositories automatically.
  maven(url = "https://maven.terraformersmc.com/")
  maven(url = "https://maven.parchmentmc.net/")
}

dependencies {
  minecraft("com.mojang:minecraft:$minecraftVersion")
  mappings(
      loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$minecraftVersion:$parchmentVersion@zip")
      })

  this.add("testmodImplementation", sourceSets.main.get().output)

  // remember to update the dependency list in fabric.mod.json
  // and the expansions in "processResources" below
  modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
  modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")

  modRuntimeOnly("com.terraformersmc:modmenu:$modMenuVersion")
}

base { archivesName.set(modId) }

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(16))
  withSourcesJar()
}

tasks.getByName<ProcessResources>("processResources") {
  filteringCharset = "UTF-8"

  inputs.property("modVersion", project.version)
  inputs.property("minecraftVersion", minecraftVersion)
  inputs.property("loaderVersion", loaderVersion)
  inputs.property("fabricVersion", fabricVersion)

  filesMatching("fabric.mod.json") {
    expand(
        mapOf(
            "modVersion" to project.version,
            "minecraftVersion" to minecraftVersion,
            "loaderVersion" to loaderVersion,
            "fabricVersion" to fabricVersion,
        ))
  }
}

val javaCompile = tasks.withType<JavaCompile> { options.encoding = "UTF-8" }

val jar = tasks.getByName<Jar>("jar") { from("LICENSE") }

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      artifact("remapJar") { builtBy(tasks.getByName("remapJar")) }
      artifact("sourcesJar") { builtBy(tasks.getByName("remapSourcesJar")) }
    }
  }
  repositories {
    // Add repositories to publish to here.
  }
}

if (versionDetails().isCleanTag) {
  // insert release configs here
  println("clean tag (todo)")
}

spotless {
  java {
    importOrder()
    prettier(mapOf("prettier" to "2.4.1", "prettier-plugin-java" to "1.5.0"))
  }

  kotlinGradle { ktfmt() }

  format("prettier") {
    target("**/*.json", "**/*.yml", "**/*.md")
    prettier("2.4.1")
  }
}

changelog {
  version.set("${gitVersion()}")
  groups.set(listOf("Changes"))
  unreleasedTerm.set("Current")
  header.set(version.get())
}

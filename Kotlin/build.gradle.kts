plugins {
	kotlin("jvm") version("1.3.50")
}

repositories {
	jcenter()
	mavenCentral()
}

dependencies {
	compile(kotlin("stdlib"))
	testCompile("io.kotlintest:kotlintest-runner-junit5:3.+")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

defaultTasks("test")

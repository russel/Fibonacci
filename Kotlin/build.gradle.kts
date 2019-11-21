plugins {
	kotlin("jvm") version("1.3.60")
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

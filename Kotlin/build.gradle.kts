buildscript {
  repositories {
    gradleScriptKotlin()
  }
  dependencies {
    classpath(kotlinModule("gradle-plugin"))
  }
}

apply {
  plugin("kotlin")
}

// targetCompatibility = 8

repositories {
  gradleScriptKotlin()
  jcenter()
  mavenCentral()
}

dependencies {
  compile(kotlinModule("stdlib"))
  testCompile("io.kotlintest:kotlintest:1.+")
}

//task wrapper(type:Wrapper) {
//  gradleVersion = '3.3'
//}

defaultTasks("test")

rootProject.name = "uape"

// Include the library modules
include(":module:core", ":module:persistence")

// Include the server module
include(":uape")
project(":uape").projectDir = file("server/api")

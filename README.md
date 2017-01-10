# Monglorious-java

[Monglorious](https://baumandm.github.io/monglorious/) is a MongoDB client library which
accepts textual queries in the syntax of the MongoDB shell.

This is a Java wrapper of the Clojure library, aiming to provide an easy-to-use and idiomatic Java interface.

Versioning of Monglorious-java is decoupled from Monglorious to allow iterating on the Java-specific functionality.  Generally it will use the latest version of Monglorious.

## Examples

    try (MongloriousClient monglorious = new MongloriousClient("mongodb://localhost:27017/testdb")) {
        long actual = monglorious.execute("db.documents.count()", Long.class);
    }

## Installation

Download the latest uberjar release from GitHub and add it to your project.

TODO: Upload to Maven Central.

## Development

### Monglorious Dependency

The Monglorious library is included as a standalone uberjar in the `lib/` folder.  
This is an AOT'd version with Clojure bundled. 
Replace this library with updated versions as needed.

### Running Tests

Tests are written in JUnit and can be run via Gradle:

    gradle test
    
### Building Uberjar

The [Shadow](http://imperceptiblethoughts.com/shadow/) plugin can build an uberjar of this project:

    gradle shadow

## License

Copyright © 2016 Dave Bauman

Distributed under the Eclipse Public License.

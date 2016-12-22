# Monglorious-java

[Monglorious](https://baumandm.github.io/monglorious/) is a MongoDB client library which
accepts textual queries in the syntax of the MongoDB shell.

This is a Java wrapper of the Clojure library, aiming to provide an easy-to-use and idiomatic Java interface.

// [![Build Status](https://travis-ci.org/baumandm/monglorious.svg?branch=master)](https://travis-ci.org/baumandm/monglorious) [![Dependencies Status](https://jarkeeper.com/baumandm/monglorious/status.svg)](https://jarkeeper.com/baumandm/monglorious)

## Examples

    MongloriousClient monglorious = new MongloriousClient("mongodb://localhost:27017/testdb");
    long actual = monglorious.execute("db.documents.count()", Long.class);

## Installation

TBD

## Development

### Running Tests

Tests are written in JUnit and can be run via Gradle:

    gradle test

## License

Copyright © 2016 Dave Bauman

Distributed under the Eclipse Public License.

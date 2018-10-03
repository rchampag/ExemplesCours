# Comparison of classical unit tests and "mockist" unit tests

This is an implementation of an example in Martin Fowler's article [Mocks Arent't Stubs](https://martinfowler.com/articles/mocksArentStubs.html).

This example specifically concentrates on comparing state-based and behavior-based (with mocks) unit testing, as described in the [Regular Tests](https://martinfowler.com/articles/mocksArentStubs.html#RegularTests) and [Tests with Mock Objects](https://martinfowler.com/articles/mocksArentStubs.html#TestsWithMockObjects) sections of the article.

This version of the example uses [JMockit](http://jmockit.github.io/) as the mocking tool. A different mocking tool is used in Fowler's original description.

* The classical folder contains a classical stae-based unit test
* The mockist folder contains the mock-based version of the same test

Both tests attempt to reproduce as closely as possible the concepts described in the article.

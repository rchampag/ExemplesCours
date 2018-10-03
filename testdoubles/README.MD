# Comparison of test stub and mock object

This is an implementation of an example in Martin Fowler's article [Mocks Arent't Stubs](https://martinfowler.com/articles/mocksArentStubs.html).

This example specifically concentrates on comparing stubs and mocks, as described in the [The Difference Between Mocks and Stubs](https://martinfowler.com/articles/mocksArentStubs.html#TheDifferenceBetweenMocksAndStubs) section of the article.

This version of the example uses [JMockit](http://jmockit.github.io/) as the mocking tool. A different mocking tool is used in Fowler's original description.

* The stub folder contains a stub-based unit test
* The mock folder contains the mock-based version of the same test

Both tests attempt to reproduce as closely as possible the concepts described in the article.

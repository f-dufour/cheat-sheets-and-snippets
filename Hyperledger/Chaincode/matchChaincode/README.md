# Intro

This is a sample smart contract that manages MatchAsset on a Hyperledger Fabric blockchain.

Users can bet money on the outcome of the match (as long as it has not been played of course).

The blockchain immutably keeps track of the match and their output, as well as the bets of the users.

# Documentation

See the Javadoc for the documentation. As it is a quick and modest project, documentation has not been emphasized.

# Further improvements

- [ ] Add new events: When someone bets…
- [ ] Better tests

# Test

Tests are performed with Mockito and JUnit.

```shell
~$ gradle test
```

Outputs:

```
> Task :cleanTest
> Task :compileJava UP-TO-DATE
> Task :processResources NO-SOURCE
> Task :classes UP-TO-DATE
> Task :compileTestJava
> Task :processTestResources NO-SOURCE
> Task :testClasses
> Task :test
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - deleteMatch called
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - matchExists called
xyz.dufour.matchcontract.MatchContractTest > assetDelete() PASSED
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - readMatch called
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - matchExists called
xyz.dufour.matchcontract.MatchContractTest > assetRead() PASSED
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - updateMatch called
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - matchExists called
xyz.dufour.matchcontract.MatchContractTest > xyz.dufour.matchcontract.MatchContractTest$AssetUpdates.updateExisting() PASSED
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - updateMatch called
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - matchExists called
xyz.dufour.matchcontract.MatchContractTest > xyz.dufour.matchcontract.MatchContractTest$AssetUpdates.updateMissing() PASSED
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - createMatch called
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - matchExists called
xyz.dufour.matchcontract.MatchContractTest > xyz.dufour.matchcontract.MatchContractTest$AssetCreates.alreadyExists() PASSED
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - createMatch called
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - matchExists called
xyz.dufour.matchcontract.MatchContractTest > xyz.dufour.matchcontract.MatchContractTest$AssetCreates.newAssetCreate() PASSED
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - matchExists called
xyz.dufour.matchcontract.MatchContractTest > xyz.dufour.matchcontract.MatchContractTest$AssetExists.noProperAsset() PASSED
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - matchExists called
xyz.dufour.matchcontract.MatchContractTest > xyz.dufour.matchcontract.MatchContractTest$AssetExists.noKey() PASSED
[Test worker] INFO xyz.dufour.matchcontract.MatchContract - matchExists called
xyz.dufour.matchcontract.MatchContractTest > xyz.dufour.matchcontract.MatchContractTest$AssetExists.assetExists() PASSED
Deprecated Gradle features were used in this build, making it incompatible with Gradle 5.0.
See https://docs.gradle.org/4.8.1/userguide/command_line_interface.html#sec:command_line_warnings
BUILD SUCCESSFUL in 2s
4 actionable tasks: 3 executed, 1 up-to-date
3:18:02 PM: Tasks execution finished ':cleanTest :test --tests "xyz.dufour.matchcontract.MatchContractTest"'.
```
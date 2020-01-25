---
title: Tips and Tricks
author: Florent Dufour
---

- When submitting a transaction, we get a answer saying everything went well. **Not true**, only the simulation passed: Consider using the event listener to get accurate feedback.
- To make chaincode reliable, must be pure function (only rely on arguments, certificates, and world state): use **deterministic operations**. Indeed, cc will be executed on the different peers, so we shouldn't rely on *unseeded random operation,* *network operations,* or *file system operation* as each peer might be configured differently.
- Size of data to store: No physical limit but mind that data are transferred over the network and stored on each peer. Try to keep data size as small as possible. Idea: Save the data on a physical drive and store the **hashes** on the chain. You can always compute the hash.
- Don't put everything in the blockchain. Only what is part of the business logic, that has to be immutable, data that we want to track.
- Mind **phantom reads/write** with couchdb (see caveat on line 143 fabric/core/chaincode/shim/interface_stable.go). See the Jira.


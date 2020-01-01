---
title: Tips and Tricks
author: Florent Dufour
---

- When submitting a transaction, we get a answer saying everything went well. **Not true**, only the simulation passed: Consider using the event listener to get accurate feedback.
- To make chaincode reliable, must be pure function (only rely on arguments, certificates, and world state): use deterministic operations. Indeed, cc will be executed on the different peers, so we shouldn't rely on network operations or file system as each peer might be configured differently.
- Mind phantom reads/write with couchdb (see caveat on line 143 fabric/core/chaincode/shim/interface_stable.go). See the Jira.

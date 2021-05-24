# Pancake contract

A sample blockchain to run on a local fabric with 1 peer and 1 orderer that mangaes (delicious) pancakes.

# Make transactions

1. createPancake:

```json
["001", "A delicious blueberry pancake"]
```

Returns:

```
[INFO] submitting transaction createPancake with args 001,Blueberry on channel mychannel
[SUCCESS] No value returned from createPancake
```

2. readPancake:

```json
["001"]
```

Returns:

```
[SUCCESS] Returned value from readPancake: {"value":"A delicious blueberry pancake"}
```

2. updatePancake:

```json
["001", "That a crêpe in fact"]
```

Returns:

```
[INFO] submitting transaction updatePancake with args 001,That a crêpe in fact
[SUCCESS] No value returned from updatePancake
```
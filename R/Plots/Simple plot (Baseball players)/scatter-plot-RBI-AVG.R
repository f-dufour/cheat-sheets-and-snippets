mlbplayers=read.table(file="mlb2017players.txt",
                      header=T, sep=" ",
                      na.strings="`",
                      stringsAsFactors=F)

playerData = mlbplayers[,c("RBI", "AVG")]

png(file="player_rbi_avg.png")
plot(x=playerData$RBI,
     y=mlbplayers$AVG,
     xlab="RBI",
     ylab="AVG",
     main="RBIs and Averages")


package xyz.dufour.matchcontract;

import xyz.dufour.matchcontract.model.Bet;
import xyz.dufour.matchcontract.model.Choice;

import java.util.ArrayList;
import java.util.List;

public class BetData {
    public static List<Bet> bets_match1() {
        List<Bet> bets = new ArrayList<>();

        // They bet on match 1
        bets.add(new Bet("Florent", "1", Choice.TEAM1, 100f, "1571998700"));
        bets.add(new Bet("Emilien", "1", Choice.TEAM2, 150f, "1571998800"));
        bets.add(new Bet("Jules", "1", Choice.TEAM2, 150f, "1571998900"));

        return bets;
    }

    public static List<Bet> bets_match2() {
        List<Bet> bets = new ArrayList<>();

        // They bet on match 2
        bets.add(new Bet("Arnaud", "2", Choice.TEAM1, 50f, "1571998700"));
        bets.add(new Bet("Jules", "2", Choice.DRAW, 100f, "1571998800"));
        bets.add(new Bet("Florent", "2", Choice.TEAM1, 70f, "1571998900"));

        return bets;
    }
}

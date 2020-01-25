package xyz.dufour.matchcontract.model;

/**
 * Finite set of type safe constants to use for choice bets.
 * <p>
 * The user can bet on one of the two teams or bet on draw.
 *
 * @see Bet
 */
public enum Choice {
    TEAM1("team1"),
    TEAM2("team2"),
    DRAW("draw");

    private String team;

    Choice(String value){
        this.team = value;
    }

    public String getTeam(){
        return this.team;
    }

}

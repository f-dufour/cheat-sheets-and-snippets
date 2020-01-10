package xyz.dufour.matchcontract.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * A bet from a user for a match.
 *
 * @see Choice
 */
@Data
@AllArgsConstructor
@ToString
public class Bet {
    /** The user that bets */
    private String user;
    /** The id of the match to bet for */
    private String matchId;
    /** Which of the 2 teams, the user bet on (can be draw) */
    private Choice choice;
    /** The amount of money the user bets (can bet cents, just has to be > 0) */
    private Float amount;
    /** The timestamp when the bet has been recorded (handled by the blockchain itself) */
    private String date;
}

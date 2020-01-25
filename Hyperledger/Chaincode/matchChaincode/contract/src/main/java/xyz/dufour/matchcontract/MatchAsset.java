package xyz.dufour.matchcontract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import xyz.dufour.matchcontract.model.Bet;
import xyz.dufour.matchcontract.model.Team;

import java.util.List;

/** A match asset immutably written on the blockchain. */
@DataType()
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchAsset {
    @Property()
    public static final String DOCTYPE = "match";
    @Property()
    private String id;
    @Property()
    private Team team1;
    @Property()
    private Team team2;
    @Property()
    private Team winner;
    @Property()
    private String date;
    @Property()
    private List<Bet> bets;

    /**
     * The minimal constructor for creating a match asset in the smart contract before the match is played
     * <p>
     * Bets and winner will be added later and recorded on the blockchain
     * @param matchId
     * @param team1
     * @param team2
     * @param date
     */
    public MatchAsset(String matchId, Team team1, Team team2, String date) {
        this.id = matchId;
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
    }

    /**
     * The minimal constructor for creating a match asset data
     *
     * @see MatchData
     * @param matchId
     * @param team1
     * @param team2
     * @param date
     */
    public MatchAsset(String matchId, Team team1, Team team2, String date, List<Bet> bets) {
        super();
        this.bets = bets;
    }

    /** Serialize a match to a JSON with the corresponding fields */
    public String toJSONString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    /** Deserialize a JSON string into a match */
    static public MatchAsset fromJSONString(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, MatchAsset.class);
    }
}

package xyz.dufour.matchcontract;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.contract.ClientIdentity;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import xyz.dufour.matchcontract.model.Bet;
import xyz.dufour.matchcontract.model.Event;
import xyz.dufour.matchcontract.model.Team;
import xyz.dufour.matchcontract.utils.Transform;

import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Contract(name = "MatchContract",
    info = @Info(title = "Match contract",
                description = "A match contract for online betting",
                version = "0.0.1"))
@Default
@Slf4j
@NoArgsConstructor
public class MatchContract implements ContractInterface {
    /** Prefix for match asset id recorded in the blockchain. Helps for indexing. */
    private final String MATCH_KEY = "match";

    // --------------------------------------------------------
    // Methods from the interface
    // --------------------------------------------------------

    @Override
    public Context createContext(ChaincodeStub stub) { return null; }

    @Override
    public void unknownTransaction(Context ctx) { }

    @Override
    public void beforeTransaction(Context ctx) { }

    @Override
    public void afterTransaction(Context ctx, Object result) { }

    // --------------------------------------------------------
    // CRUD operations
    // --------------------------------------------------------

    /**
     * Does a match exist in the world state ?
     * <p>
     * This does not create a transaction on the blockchain
     *
     * @param ctx
     * @param matchId The id of the match
     * @return
     */
    public boolean matchExists(Context ctx, String matchId) {
        log.info("matchExists called");

        String id = resolveId(matchId);
        byte[] buffer = ctx.getStub().getState(id);
        return (buffer != null && buffer.length > 0);
    }

    /**
     * Create a match in the world state.
     * <p>
     * Bets and winner will be added later and recorded on the blockchain.
     * <p>
     * This creates a transaction on the blockchain
     *
     * @param ctx
     * @param matchId the id of the match
     * @param team1 name of the first team
     * @param team2 name of the second team
     * @param date date of the match
     */
    @Transaction()
    public void createMatch(Context ctx, String matchId, Team team1, Team team2, String date) {
        log.info("createMatch called");

        boolean exists = matchExists(ctx, matchId);
        if (exists) {
            throw new RuntimeException("The match " + matchId + " already exists");
        }

        MatchAsset matchAsset = new MatchAsset(matchId, team1, team2, date);
        String id = resolveId(matchId);
        byte[] value =  matchAsset.toJSONString().getBytes(UTF_8);
        ctx.getStub().putState(id, value);
    }

    /**
     * If the match exists in the world state, this method parses the JSON response and creates a Match asset POJO
     * <p>
     * Bets and winner will be added later and recorded on the blockchain.
     * <p>
     * This does not create a transaction on the blockchain
     *
     * @see MatchAsset
     * @param ctx
     * @param matchId
     * @return
     */
    public MatchAsset readMatch(Context ctx, String matchId) {
        log.info("readMatch called");

        boolean exists = matchExists(ctx, matchId);
        if (!exists) {
            throw new RuntimeException("The match " + matchId + " does not exist");
        }

        String id = resolveId(matchId);
        MatchAsset matchAsset = MatchAsset.fromJSONString(new String(ctx.getStub().getState(id), UTF_8)); // TODO: fromJSONString
        return matchAsset;
    }

    /**
     * Update a Match before it is being played. For example, if the teams or the date change.
     * <p>
     * This creates a transaction on the blockchain.
     *
     * @param ctx
     * @param matchId
     * @param team1
     * @param team2
     * @param number
     */
    @Transaction()
    public void updateMatch(Context ctx, String matchId, Team team1, Team team2, String number) {
        log.info("updateMatch called");

        boolean exists = matchExists(ctx,matchId);
        if (!exists) {
            throw new RuntimeException("The match " + matchId + " does not exist");
        }
        MatchAsset asset = new MatchAsset(matchId, team1, team2, number);

        String id = resolveId(matchId);
        byte[] value =  asset.toJSONString().getBytes(UTF_8);

        ctx.getStub().putState(id, value);
    }

    /**
     * Delete a match from the world state.
     * <p>
     * It can happen if a match won't be played.
     * <p>
     * This creates a transaction on the blockchain
     * @param ctx
     * @param matchId
     */
    @Transaction()
    public void deleteMatch(Context ctx, String matchId) {
        log.info("deleteMatch called");
        
        boolean exists = matchExists(ctx,matchId);
        if (!exists) {
            throw new RuntimeException("The match " + matchId + " does not exist");
        }
        
        String id = resolveId(matchId);
        ctx.getStub().delState(id);
    }

    // --------------------------------------------------------
    // Extra operations
    // --------------------------------------------------------

    /**
     * Get all match in the world state
     * <p>
     * This does not create a transaction
     *
     * @param ctx
     * @return
     */
    public List<MatchAsset> getAllMatch(Context ctx) {
        log.info("getAllMatch called");

        String startKey = resolveId("0");
        String endKey = resolveId("z");
        QueryResultsIterator<KeyValue> matchIterator = ctx.getStub().getStateByRange(startKey, endKey);

        List<String> allMatchAsStrings = new ArrayList<>();
        List<MatchAsset> allMatchAssets = new ArrayList<>();

        List<KeyValue> allMatchKeyValue = Transform.iterableToList(matchIterator);
        allMatchKeyValue.forEach(kv -> allMatchAsStrings.add(kv.getStringValue()));
        allMatchAsStrings.forEach(s -> allMatchAssets.add(MatchAsset.fromJSONString(s)));

        return allMatchAssets;
    }

    /**
     * Get history of an asset by querying the blockchain
     * <p>
     * This does not create a transaction
     *
     * @param ctx
     * @param matchId
     * @return
     */
    public List<MatchAsset> getMatchHistory(Context ctx, String matchId) {
        log.info("getMatchHistory called");

        boolean matchExists = matchExists(ctx, matchId);
        if (!matchExists){
            throw new RuntimeException("The match "+matchId+" does not exist");
        }

        String id = resolveId(matchId);
        QueryResultsIterator<KeyModification> historyForKey = ctx.getStub().getHistoryForKey(id);

        List<String> historyAsStrings = new ArrayList<>();
        List<MatchAsset> history = new ArrayList<>();

        List<KeyModification> keyModification = Transform.iterableToList(historyForKey);
        keyModification.forEach(km -> historyAsStrings.add(km.getStringValue()));
        historyAsStrings.forEach(s -> history.add(MatchAsset.fromJSONString(s)));

        return history;
    }

    // --------------------------------------------------------
    // Operations on existing match (bets or results)
    // --------------------------------------------------------

    /**
     * Once the match is playes, we can immutably set the winner.
     * <p>
     * This creates a transaction on the blockchain
     *
     * @param ctx
     * @param matchId
     * @param winner
     */
    @Transaction()
    public void setMatchWinner(Context ctx, String matchId, Team winner) {
        log.info("setMatchWinner called");

        boolean matchExists = matchExists(ctx, matchId);
        if (!matchExists){
            throw new RuntimeException("The match " + matchId + " does not exist");
        }

        MatchAsset match = readMatch(ctx, matchId);
        if(!List.of(Team.values()).contains(winner)){
            throw new RuntimeException("Provide a valid winner, " + winner + " is not available among: " + Team.values());
        }

        match.setWinner(winner);
        String id = resolveId(matchId);
        byte[] value =  match.toJSONString().getBytes(UTF_8);
        ctx.getStub().putState(id, value);
    }

    /**
     *
     * User are able to bet on a match
     * <p>
     * A match should exist in the world state and not have been played already.
     * The bet should be positive
     *
     * @param ctx
     * @param matchId
     * @param user
     * @param amount
     * @param choice
     */
    @Transaction()
    public void addBetMatch(Context ctx, String matchId, String user, Float amount, Team choice) {
        log.info("addBetMatch called");

        MatchAsset match = readMatch(ctx, matchId);
        if (match.getWinner() != null){
            throw new RuntimeException("Forbidden to bet on a match that already has a winner. The winner is: " + match.getWinner());
        }

        if (amount != null || amount <= 0){
            throw new RuntimeException("Provide valid amount for bet");
        }

        if(!List.of(Team.values()).contains(choice)){
            throw new RuntimeException("Provide a valid winner. " + choice + " is not available among: " + Team.values());
        }

        Bet bet = new Bet(user, choice, amount, String.valueOf(ctx.getStub().getTxTimestamp().getEpochSecond()));
        // But normally, use the client identity
        // bet.setUser(ctx.getClientIdentity().getAttributeValue("hf.EnrollementID"));

        if (match.getBets() == null || match.getBets().size() == 0) {
            match.setBets(List.of(bet));
        } else {
            match.getBets().add(bet);
        }

        String id = resolveId(matchId);
        byte[] value = match.toJSONString().getBytes(UTF_8);
        ctx.getStub().putState(id, value);

        //Event
        String event = "{\"bet\": \"" + bet + "\","
                        + "\"matchId\": \"" + matchId + "\"}";
        byte[] payload = event.getBytes(UTF_8);
        ctx.getStub().setEvent(Event.ADD_BET.toString(), payload);
    }

    // --------------------------------------------------------
    // Rich query with CouchDB
    // --------------------------------------------------------

    public List<MatchAsset> getAllMatchWithQuery(Context ctx, String query) {
        List<KeyValue> stateList = Transform.iterableToList(ctx.getStub().getQueryResult(query));

        List<String> allMatchAsStrings = new ArrayList<>();
        List<MatchAsset> allMatchAssets = new ArrayList<>();

        stateList.forEach(kv -> allMatchAsStrings.add(kv.getStringValue()));
        allMatchAsStrings.forEach(s -> allMatchAssets.add(MatchAsset.fromJSONString(s)));

        return allMatchAssets;
    }

    public List<MatchAsset> getAllMatchWithSpecificTeam(Context ctx, Team team) {
        return this.getAllMatchWithQuery(ctx, "{\"selector\":{\"$or\":[{\"team1\":{\"$eq\":\"" + team.toString() + "\"}},{\"team2\":{\"$eq\":\"" + team.toString() + "\"}}]}}");
    }

    // --------------------------------------------------------
    // Fabric CA
    // --------------------------------------------------------

    public ClientIdentity readIdentity(Context ctx){
        log.info("id: " + ctx.getClientIdentity().getId());
        log.info("EnrollementID: " + ctx.getClientIdentity().getAttributeValue("hf.EnrollementID"));
        log.info("Affiliation: " + ctx.getClientIdentity().getAttributeValue("hf.Affiliation"));
        return ctx.getClientIdentity();
    }

    private String resolveId(String matchId) {
        return this.MATCH_KEY + "." + matchId;
    }
}

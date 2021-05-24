package xyz.dufour.matchcontract;

import xyz.dufour.matchcontract.model.Team;

import java.util.ArrayList;
import java.util.List;

public class MatchData {
    public static List<MatchAsset> matchAssets() {
        List<MatchAsset> matchAssets = new ArrayList<>();

        matchAssets.add(new MatchAsset("1", Team.PSG, Team.OL, "1587754329", BetData.bets_match1()));
        matchAssets.add(new MatchAsset("2", Team.ASNL, Team.OM, "1587754329", BetData.bets_match1()));

        return matchAssets;


    }
}

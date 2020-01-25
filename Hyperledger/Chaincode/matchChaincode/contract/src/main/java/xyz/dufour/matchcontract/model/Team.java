package xyz.dufour.matchcontract.model;

/** Finite set of type safe constants to use for teams.
 * <p>
 * A minimal set of French soccer teams
 */
public enum Team {
    PSG("Paris Saint-Germain"),
    OM("Olympique de Marseille"),
    OL("Olympique Lyonnais"),
    ASNL("AS Nancy Lorraine"),
    RCS("Racing Club Strasbourg");

    private String team;

    Team(String team) {
        this.team = team;
    }

    public String toString() {
        return this.team;
    }
}

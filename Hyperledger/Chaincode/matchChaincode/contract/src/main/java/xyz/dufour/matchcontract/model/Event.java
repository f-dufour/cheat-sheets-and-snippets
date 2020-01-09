package xyz.dufour.matchcontract.model;

/** Finite set of type safe constants to use for events */
public enum Event {
    ADD_BET("add-bet-event");


    private String event;

    Event(String event){
        this.event = event;
    }

    @Override
    public String toString() {
        return this.event;
    }
}

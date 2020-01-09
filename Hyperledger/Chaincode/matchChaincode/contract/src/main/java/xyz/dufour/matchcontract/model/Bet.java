package xyz.dufour.matchcontract.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
public class Bet {
    private String user;
    private Team choice;
    private Float amount;
    private String date;
}

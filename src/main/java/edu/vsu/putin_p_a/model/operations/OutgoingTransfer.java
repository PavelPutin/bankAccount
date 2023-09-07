package edu.vsu.putin_p_a.model.operations;

import edu.vsu.putin_p_a.model.Account;
import edu.vsu.putin_p_a.model.Money;

import java.util.UUID;

public class OutgoingTransfer extends Operation {
    Account sender;
    Account recipient;
    Money value;
    String description;
}

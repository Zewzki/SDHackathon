package client.util;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction {

    private StringProperty sender;
    private StringProperty recipient;
    private StringProperty amount;
    private StringProperty timeStamp;
    private StringProperty comment;

    public Transaction(String sender, String recipient, String amount, String timeStamp, String comment) {

        this.sender = new SimpleStringProperty(this, sender);
        this.recipient = new SimpleStringProperty(this, recipient);
        this.amount = new SimpleStringProperty(this, amount);
        this.timeStamp = new SimpleStringProperty(this, timeStamp);
        this.comment = new SimpleStringProperty(this, comment);

    }

    public StringProperty getSender() { return sender; }
    public StringProperty getRecipient() { return recipient; }
    public StringProperty getAmount() { return amount; }
    public StringProperty getTimeStamp() { return timeStamp; }
    public StringProperty getComment() { return comment; }

}

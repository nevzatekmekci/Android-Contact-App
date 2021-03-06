package com.example.nevzat.project.models;


public class ActivityStatistic {
    private int asid;
    private int missingCalls;
    private int sentMessages;
    private int receivedMessages;
    private int incomingCallsNumber;
    private int outgoingCallsNumber;
    private int incomingCallsDuration;
    private int outgoingCallsDuration;

    public ActivityStatistic() {
        this.missingCalls = 0;
        this.sentMessages = 0;
        this.receivedMessages = 0;
        this.incomingCallsNumber = 0;
        this.outgoingCallsNumber = 0;
        this.incomingCallsDuration = 0;
        this.outgoingCallsDuration = 0;
    }

    public ActivityStatistic(int missingCalls, int sentMessages, int receivedMessages, int incomingCallsNumber,
                             int outgoingCallsNumber, int incomingCallsDuration, int outgoingCallsDuration) {
        this.missingCalls = missingCalls;
        this.sentMessages = sentMessages;
        this.receivedMessages = receivedMessages;
        this.incomingCallsNumber = incomingCallsNumber;
        this.outgoingCallsNumber = outgoingCallsNumber;
        this.incomingCallsDuration = incomingCallsDuration;
        this.outgoingCallsDuration = outgoingCallsDuration;
    }

    public int getAsid() {
        return asid;
    }

    public void setAsid(int asid) {
        this.asid = asid;
    }

    public int getMissingCalls() {
        return missingCalls;
    }

    public void setMissingCalls(int missingCalls) {
        this.missingCalls = missingCalls;
    }

    public int getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(int sentMessages) {
        this.sentMessages = sentMessages;
    }

    public int getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(int receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public int getIncomingCallsNumber() {
        return incomingCallsNumber;
    }

    public void setIncomingCallsNumber(int incomingCallsNumber) {
        this.incomingCallsNumber = incomingCallsNumber;
    }

    public int getOutgoingCallsNumber() {
        return outgoingCallsNumber;
    }

    public void setOutgoingCallsNumber(int outgoingCallsNumber) {
        this.outgoingCallsNumber = outgoingCallsNumber;
    }

    public int getIncomingCallsDuration() {
        return incomingCallsDuration;
    }

    public void setIncomingCallsDuration(int incomingCallsDuration) {
        this.incomingCallsDuration = incomingCallsDuration;
    }

    public int getOutgoingCallsDuration() {
        return outgoingCallsDuration;
    }

    public void setOutgoingCallsDuration(int outgoingCallsDuration) {
        this.outgoingCallsDuration = outgoingCallsDuration;
    }
}

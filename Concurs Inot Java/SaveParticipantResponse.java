package me.networking;

import me.model.Participant;

public class SaveParticipantResponse implements Response {
    private Participant p;

    public SaveParticipantResponse(Participant p) {
        this.p = p;
    }

    public Participant getParticipant() {
        return p;
    }
}

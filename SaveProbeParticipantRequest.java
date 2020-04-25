package me.networking;

import java.util.ArrayList;

public class SaveProbeParticipantRequest implements Request {
    private ArrayList<Integer> probe;
    private int idParticipant;

    public SaveProbeParticipantRequest(ArrayList<Integer> probe, int idParticipant) {
        this.probe = probe;
        this.idParticipant = idParticipant;
    }

    public ArrayList<Integer> getProbe() {
        return probe;
    }

    public int getIdParticipant() {
        return idParticipant;
    }
}

package me.networking;

public class GetParticipantiDTOCuProbaPRequest implements Request{
    private int p;
    public GetParticipantiDTOCuProbaPRequest(int p){
        this.p=p;
    }
    public int getProba(){return p;}
}

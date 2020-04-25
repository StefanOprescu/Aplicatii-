package me.networking;

import me.model.ParticipantDTO;

import java.util.List;

public class GetParticipantiDTOCuProbaPResponse implements Response {
    public List<ParticipantDTO> pDTO;
    public GetParticipantiDTOCuProbaPResponse(List<ParticipantDTO> pDTO){
        this.pDTO = pDTO;
    }
    public List<ParticipantDTO> getpDTO() { return pDTO; }
}

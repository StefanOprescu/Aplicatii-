package me.networking;

import me.model.ProbaDTO;

import java.util.List;

public class GetProbeDTOResponse implements Response {
    private List<ProbaDTO> probeDTO;
    public GetProbeDTOResponse(List<ProbaDTO> lista){
        probeDTO = lista;
    }
    public List<ProbaDTO> getProbeDTO() {return probeDTO;}
}

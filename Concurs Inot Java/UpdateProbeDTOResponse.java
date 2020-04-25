package me.networking;

import me.model.ProbaDTO;

import java.util.List;

public class UpdateProbeDTOResponse implements UpdateResponse {
    private List<ProbaDTO> probeDTO;

    public UpdateProbeDTOResponse(List<ProbaDTO> probeDTO) {
        this.probeDTO = probeDTO;
    }

    public List<ProbaDTO> getProbeDTO() {
        return probeDTO;
    }
}

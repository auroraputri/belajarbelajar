package apap.tutorial.belajarbelajar.service;

import java.util.HashMap;
import java.util.List;

import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.rest.PengajarDetail;
import reactor.core.publisher.Mono;

public interface PengajarRestService {
    PengajarModel createPengajar(PengajarModel pengajar);
    List<PengajarModel> retrieveListPengajar();
    PengajarModel getPengajarByNoPengajar(Long noPengajar);
    PengajarModel getPengajarGenderByNoPengajar(Long noPengajar);
    PengajarModel updatePengajar(Long noPengajar, PengajarModel pengajarUpdate);
    void deletePengajar(Long noPengajar);
}

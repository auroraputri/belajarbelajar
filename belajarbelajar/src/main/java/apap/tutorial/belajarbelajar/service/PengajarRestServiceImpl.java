package apap.tutorial.belajarbelajar.service;

import apap.tutorial.belajarbelajar.model.PengajarModel;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import apap.tutorial.belajarbelajar.rest.PengajarDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tutorial.belajarbelajar.repository.PengajarDb;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PengajarRestServiceImpl implements PengajarRestService {
    private final WebClient webClient;

    @Autowired
    private PengajarDb pengajarDb;

    public PengajarRestServiceImpl(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl("https://api.genderize.io").build();
    }

    @Override
    public PengajarModel createPengajar(PengajarModel pengajar) {
        return pengajarDb.save(pengajar);
    }

    @Override
    public List<PengajarModel> retrieveListPengajar() {
        return pengajarDb.findAll();
    }

    @Override
    public PengajarModel getPengajarByNoPengajar(Long noPengajar) {
        Optional<PengajarModel> pengajar = pengajarDb.findByNoPengajar(noPengajar);
        if (pengajar.isPresent()){
            return pengajar.get();
        }
        else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public PengajarModel getPengajarGenderByNoPengajar(Long noPengajar) {
        LocalDateTime now = LocalDateTime.now();
        PengajarModel pengajar = getPengajarByNoPengajar(noPengajar);
        String namaPengajar = pengajar.getNamaPengajar();

        if ((now.isBefore(pengajar.getCourse().getTanggalDimulai()) || now.isAfter(pengajar.getCourse().getTanggalBerakhir()))) {
            Mono<PengajarDetail> jenisKelaminPengajar = this.webClient.get().uri("?name=" + namaPengajar.split(" ")[0]).retrieve().bodyToMono(PengajarDetail.class);

            if (jenisKelaminPengajar.block().getGender().equals("male")) {
                pengajar.setJenisKelamin(false);
            } else {
                pengajar.setJenisKelamin(true);
            }

            pengajarDb.save(pengajar);
            return pengajar;

        } else {
            throw new UnsupportedOperationException("Error!");
        }
    }

    @Override
    public PengajarModel updatePengajar(Long noPengajar, PengajarModel pengajarUpdate) {
        PengajarModel pengajar = getPengajarByNoPengajar(noPengajar);
        pengajar.setNamaPengajar(pengajarUpdate.getNamaPengajar());
        pengajar.setCourse(pengajarUpdate.getCourse());
        pengajar.setIsPengajarUniversitas(pengajarUpdate.getIsPengajarUniversitas());
        return pengajarDb.save(pengajar);
    }

    @Override
    public void deletePengajar(Long noPengajar) {
        PengajarModel pengajar = getPengajarByNoPengajar(noPengajar);
        if (isClose(pengajar.getCourse().getTanggalDimulai(), pengajar.getCourse().getTanggalBerakhir())){
            pengajarDb.delete(pengajar);
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean isClose(LocalDateTime tanggalDimulai, LocalDateTime tanggalBerakhir){
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(tanggalDimulai)|| now.isAfter(tanggalBerakhir)){
            return true;
        }
        return false;
    }
}

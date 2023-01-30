package apap.tutorial.belajarbelajar.repository;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PengajarDb extends JpaRepository<PengajarModel, Long> {
    //JPA
    Optional<PengajarModel> findByNoPengajar(Long noPengajar);
}

package apap.tutorial.belajarbelajar.repository;

import apap.tutorial.belajarbelajar.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDB extends JpaRepository<RoleModel, Long> {
}

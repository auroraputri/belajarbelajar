package apap.tutorial.belajarbelajar.service;

import apap.tutorial.belajarbelajar.model.RoleModel;
import apap.tutorial.belajarbelajar.repository.RoleDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDB roleDb;

    @Override
    public void addRole(RoleModel role) {
        roleDb.save(role);
    }

    @Override
    public RoleModel getRoleById(Long id) {
        Optional<RoleModel> role = roleDb.findById(id);
        if (role.isPresent()){
            return role.get();
        }
        else{
            return null;
        }
    }

    @Override
    public List<RoleModel> findAll() {
        List<RoleModel> listRole = roleDb.findAll();
        return listRole;
    }
}

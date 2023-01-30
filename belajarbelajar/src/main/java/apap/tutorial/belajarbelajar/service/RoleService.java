package apap.tutorial.belajarbelajar.service;

import apap.tutorial.belajarbelajar.model.RoleModel;
import apap.tutorial.belajarbelajar.model.UserModel;

import java.util.List;

public interface RoleService {

    void addRole(RoleModel role);
    RoleModel getRoleById (Long id);

    List<RoleModel> findAll();
}


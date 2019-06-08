package edu.nju.cinemasystem.blservices.user;

import edu.nju.cinemasystem.data.po.User;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.RegistryForm;
import edu.nju.cinemasystem.data.vo.form.UserForm;

import javax.validation.constraints.NotNull;

public interface Account {
    Response register(RegistryForm registryForm);
    Response login(UserForm userForm);

    User getUserByID(@NotNull int ID);
}

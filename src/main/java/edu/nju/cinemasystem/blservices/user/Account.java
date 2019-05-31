package edu.nju.cinemasystem.blservices.user;

import edu.nju.cinemasystem.data.vo.RegistryForm;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.UserForm;

public interface Account {
    Response register(RegistryForm registryForm);
    Response login(UserForm userForm);
}

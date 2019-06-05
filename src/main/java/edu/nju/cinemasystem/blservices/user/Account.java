package edu.nju.cinemasystem.blservices.user;

import edu.nju.cinemasystem.data.vo.Form.RegistryForm;
import edu.nju.cinemasystem.data.vo.Form.UserForm;
import edu.nju.cinemasystem.data.vo.Response;

public interface Account {
    Response register(RegistryForm registryForm);
    Response login(UserForm userForm);
}

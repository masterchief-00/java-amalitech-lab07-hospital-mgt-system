package com.kwizera.javaamalitechlab07hospitalmgtsystem.dao;

import com.kwizera.javaamalitechlab07hospitalmgtsystem.models.Role;

import java.util.List;

public interface RoleDAO {
    Role findById(int id);

    List<Role> findAll();

    void save(Role role);

    void update(Role role);

    void delete(int id);
}

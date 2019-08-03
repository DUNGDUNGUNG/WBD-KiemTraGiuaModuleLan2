package com.codegym.service.impl;

import com.codegym.model.Receptionist;
import com.codegym.repository.ReceptionistRepository;
import com.codegym.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReceptionistServiceImpl implements ReceptionistService {

    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Override
    public List<Receptionist> findAll() {
        return receptionistRepository.findAll();
    }

    @Override
    public void save(Receptionist receptionist) {
        receptionistRepository.save(receptionist);
    }

    @Override
    public void update(int id, Receptionist receptionist) {
        receptionistRepository.update(id, receptionist);
    }

    @Override
    public void remove(int id) {
        receptionistRepository.remove(id);
    }

    @Override
    public Receptionist findById(int id) {
        return receptionistRepository.findById(id);
    }

    @Override
    public Receptionist findByName(String name) {
        return receptionistRepository.findByName(name);
    }
}

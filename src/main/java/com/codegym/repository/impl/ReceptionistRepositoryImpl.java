package com.codegym.repository.impl;

import com.codegym.model.Receptionist;
import com.codegym.repository.ReceptionistRepository;

import java.util.ArrayList;
import java.util.List;

public class ReceptionistRepositoryImpl implements ReceptionistRepository {

    private static List<Receptionist> receptionistList;

    static {
        receptionistList = new ArrayList<>();
        receptionistList.add(new Receptionist(1, "Nguyen", 21, "Ha noi", "bong da", "soi.jpeg"));
        receptionistList.add(new Receptionist(2, "Anh", 22, "Ha Lan", "Xem phim", "ho.jpeg"));
        receptionistList.add(new Receptionist(3, "Tran", 24, "Nghe An", "choi game", "meo.jpeg"));
        receptionistList.add(new Receptionist(4, "Le", 19, "Sai gon", "du lich", "conmat.jpeg"));
    }

    @Override
    public List<Receptionist> findAll() {
        return new ArrayList<>(receptionistList);
    }

    @Override
    public void save(Receptionist receptionist) {
        receptionistList.add(receptionist);
    }

    @Override
    public void update(int id, Receptionist receptionist) {
        int index = 0;
        for (int i = 0; i < receptionistList.size(); i++) {
            if (receptionistList.get(i).getId() == id) {
                index = i;
            }
        }
        receptionistList.set(index, receptionist);
    }

    @Override
    public void remove(int id) {
        Receptionist receptionist = this.findById(id);
        receptionistList.remove(receptionist);

    }

    @Override
    public Receptionist findById(int id) {
        int index = 0;
        for (int i = 0; i < receptionistList.size(); i++) {
            if (receptionistList.get(i).getId() == id) {
                index = i;
            }
        }
        return receptionistList.get(index);
    }

    @Override
    public Receptionist findByName(String name) {
        for (Receptionist receptionist : receptionistList) {
            String temp = receptionist.getName();
            if (temp.equals(name)) {
                return receptionist;
            }
        }
        return null;
    }
}

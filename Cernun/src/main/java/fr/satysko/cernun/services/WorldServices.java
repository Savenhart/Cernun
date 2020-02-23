package fr.satysko.cernun.services;

import fr.satysko.cernun.dao.GenericDAO;
import fr.satysko.cernun.interfaces.GenericServices;
import fr.satysko.cernun.models.World;

import java.util.List;

public class WorldServices implements GenericServices<World> {

    GenericDAO<World> dao;

    public WorldServices(String persistenceUnitNameTu) {
        dao = new GenericDAO<>(persistenceUnitNameTu, World.class);
    }

    @Override
    public List<World> findAll() {
        return null;
    }

    @Override
    public World find(int id) {
        return dao.findById(id);
    }

    @Override
    public World create(World world) {
        dao.saveOrUpdate(world);
        return world;
    }

    @Override
    public World update(World world) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}

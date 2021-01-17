package com.common.crud.service.impl;

import com.common.crud.service.AbstractService;
import com.common.crud.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class AbstractServiceImpl<ID extends Number, E extends Entity, DAO extends JpaRepository<E, ID>> implements AbstractService<ID, E> {

	@Autowired
	private DAO dao;
	
	@Override
	public List<E> getAll() {
		return getDao().findAll();
	}

	@Override
	public E getBy(ID id) {
		return dao.findById(id).get();
	}

	@Override
	public void delete(E entity) {
		dao.delete(entity);
	}

	@Override
	public void deleteBy(ID id) {
		dao.deleteById(id);		
	}

	@Override
	public E save(E entity) {
		return dao.save(entity);
	}

	public DAO getDao() {
		return dao;
	}

}

package com.common.crud.service;

import com.common.crud.entity.Entity;

import java.util.List;

public interface AbstractService<ID extends Number, E extends Entity> {

	List<E> getAll();
	
	E getBy(ID id);
	
	void delete(E entity);
	
	void deleteBy(ID id);
	
	E save(E entity);

}

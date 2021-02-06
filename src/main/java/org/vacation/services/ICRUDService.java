package org.vacation.services;

public interface ICRUDService<T, R> {

	T create(T t);

	T getById(R r); // get by Id

	T update(R r, T t);

	void delete(R r);

}

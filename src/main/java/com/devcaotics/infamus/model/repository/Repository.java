package com.devcaotics.infamus.model.repository;

import com.devcaotics.infamus.model.entities.Professor;

import java.sql.SQLException;

public interface Repository <C, KEY>{

	public void create(C c) throws SQLException;
	public Professor update(C c) throws SQLException;
	public C read(KEY k) throws SQLException;
	public void delete(KEY k) throws SQLException;

	C readByEmail(String email) throws SQLException;
}

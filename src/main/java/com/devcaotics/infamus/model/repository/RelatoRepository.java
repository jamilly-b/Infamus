package com.devcaotics.infamus.model.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.devcaotics.infamus.model.entities.Estudante;
import com.devcaotics.infamus.model.entities.Professor;
import com.devcaotics.infamus.model.entities.Relato;

public final class RelatoRepository implements Repository<Relato, Integer> {

	protected RelatoRepository() {

	}

	@Override
	public void create(Relato c) throws SQLException {
		// TODO Auto-generated method stub

		String sql = "insert into relato(data_relato, descricao, codigo_fk_estudante) values (?, ?, ?)";

		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

		pstm.setDate(1, new java.sql.Date(c.getData().getTime()));
		pstm.setBytes(2, c.getDescricao().getBytes());
		pstm.setInt(3, c.getEstudante().getCodigo());

		pstm.execute();
	}

	@Override
	public void update(Relato c) {
		// TODO Auto-generated method stub

	}

	@Override
	public Relato read(Integer k) throws SQLException {
		// TODO Auto-generated method stub

		Relato r = null;

		String sql = "select * from relato as r join estudante as e on(r.codigo_fk_estudante = e.codigo_estudante) where r.codigo_relato = "
				+ k;

		ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();

		if (result.next()) {

			r = new Relato();

			r.setCodigo(k);
			r.setData(new Date(result.getDate("data_relato").getTime()));
			r.setDescricao(new String(result.getBytes("descricao")));

			Estudante e = new Estudante();
			e.setCodigo(result.getInt("codigo_estudante"));
			e.setNome(result.getString("nome_estudante"));
			e.setMatricula(result.getString("matricula_estudante"));
			e.setEndereco(result.getString("endereco_estudante"));
			e.setEmail(result.getString("email_estudante"));
			e.setTelefone(result.getString("telefone_estudante"));
			e.setAnoEntrada(result.getInt("ano_entrada"));

			r.setEstudante(e);
		}

		return r;
	}

	@Override
	public void delete(Integer k) {
		// TODO Auto-generated method stub

	}

	public List<Relato> filterByCodigoEstudante(int codigoEstudante) throws SQLException {

		String sql = "select * from relato as r join estudante as e on(r.codigo_fk_estudante = e.codigo_estudante) where r.codigo_fk_estudante = "
				+ codigoEstudante;

		ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();

		List<Relato> relatos = new ArrayList<Relato>();

		Estudante e = null;

		while (result.next()) {

			if (e == null) {
				e = new Estudante();
				e.setCodigo(result.getInt("codigo_estudante"));
				e.setNome(result.getString("nome_estudante"));
				e.setMatricula(result.getString("matricula_estudante"));
				e.setEndereco(result.getString("endereco_estudante"));
				e.setEmail(result.getString("email_estudante"));
				e.setTelefone(result.getString("telefone_estudante"));
				e.setAnoEntrada(result.getInt("ano_entrada"));

			}

			Relato r = new Relato();

			r.setCodigo(result.getInt("codigo_relato"));
			r.setData(new Date(result.getDate("data_relato").getTime()));
			r.setDescricao(new String(result.getBytes("descricao")));
			
			r.setEstudante(e);
			
			relatos.add(r);

		}
		return relatos;

	}

	public List<Relato> filterRelatoByEmailProfessor(String email) throws SQLException {
		String sql = "select * from relato as r join professor as p on(r.codigo_fk_estudante = p.email_professor) where r.codigo_fk_professor = "
				+ email;

		ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();

		List<Relato> relatos = new ArrayList<Relato>();

		Professor p = null;

		while(result.next()) {

			if(p == null) {
				p = new Professor();

				p.setNome(result.getString("nome_professor"));
				p.setEmail(result.getString("email_professor"));
				p.setSenha(result.getString("senha_professor"));
			}

			Relato r = new Relato();

			r.setCodigo(result.getInt("codigo_relato"));
			r.setData(new Date(result.getDate("data_relato").getTime()));
			r.setDescricao(new String(result.getBytes("descricao")));

			r.setProfessor(p);

			relatos.add(r);
		}

		return relatos;
	}
	
	public static void main(String args[]) {
		
		Relato r = new Relato();
		r.setData(new Date());
		r.setDescricao("Me fez raiva o dia todo");
		Estudante e =  new Estudante();
		e.setCodigo(2);
		
		r.setEstudante(e);
		
		RelatoRepository rr = new RelatoRepository();
		
		try {
			rr.create(r);
			
			r = new Relato();
			r.setData(new Date());
			r.setDescricao("Me fez raiva o dia todo e a noite também");
			r.setEstudante(e);
			
			rr.create(r);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}

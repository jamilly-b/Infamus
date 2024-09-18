package com.devcaotics.infamus.model.repository;

import com.devcaotics.infamus.model.entities.Estudante;
import com.devcaotics.infamus.model.entities.Professor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorRepository implements Repository<Professor, String>{


    protected ProfessorRepository() {

    }

    @Override
    public void create(Professor professor) throws SQLException {

        String sql = "insert into professor(nome_professor, email_professor, senha_professor) values (?,?,?)";

        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, professor.getNome());
        pstm.setString(2, professor.getEmail());
        pstm.setString(3, professor.getSenha());

        pstm.execute();
    }

    @Override
    public void update(Professor professor) throws SQLException {
        String sql = "update professor set nome_professor=?, email_professor=?, senha_professor=?";

        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, professor.getNome());
        pstm.setString(2, professor.getEmail());
        pstm.setString(3, professor.getSenha());

        pstm.executeUpdate();
    }

    @Override
    public Professor read(String k) throws SQLException {
        String sql = "select * from professor where email = ?";

        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, k);

        ResultSet result = pstm.executeQuery();

        if(result.next()) {

            Professor p =  new Professor();

            p.setNome(result.getString("nome_professor"));
            p.setEmail(result.getString("email_professor"));
            p.setSenha(result.getString("senha_professor"));

            return p;
        }

        return null;
    }

    public List<Professor> readAll() throws SQLException{
        String sql = "select * from professor";

        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        List<Professor> professores = new ArrayList<>();

        ResultSet result = pstm.executeQuery();

        while(result.next()) {

            Professor p =  new Professor();

            p.setNome(result.getString("nome_professor"));
            p.setEmail(result.getString("email_professor"));
            p.setSenha(result.getString("senha_professor"));

            professores.add(p);
        }

        return professores;
    }

    @Override
    public void delete(String k) throws SQLException {
        String sql = "delete from professor where professor = "+k;

        ConnectionManager.getCurrentConnection().prepareStatement(sql).execute();
    }




    public static void main(String args[]) {

        Professor p = new Professor();

        p.setNome("Ártico Pereira Asiático");
        p.setEmail("americo_aziatico@gmail.com");
        p.setSenha("123");


        try {
            new ProfessorRepository().create(p);

            //Professor p = new ProfessorRepository().read("americo_aziatico@gmail.com");
            //System.out.println(p.getNome());

            new ProfessorRepository().delete("americo_aziatico@gmail.com");
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Administrator
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Unidade;
import util.ConnectionFactory;

public class UnidadeDAO {


    public void adicionar(Unidade unidade) {
        String sql = "INSERT INTO unidades(codigo, nome, descricao) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, unidade.getCodigo());
            stmt.setString(2, unidade.getNome());
            stmt.setString(3, unidade.getDescricao());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Unidade cadastrada com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar unidade: " + e.getMessage());
        }
    }

 
    public List<Unidade> listar() {
        String sql = "SELECT * FROM unidades";
        List<Unidade> unidades = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Unidade unidade = new Unidade();
                unidade.setId(rs.getInt("id"));
                unidade.setCodigo(rs.getString("codigo"));
                unidade.setNome(rs.getString("nome"));
                unidade.setDescricao(rs.getString("descricao"));

                unidades.add(unidade);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar unidades: " + e.getMessage());
        }
        return unidades;
    }

   
    public void atualizar(Unidade unidade) {
        String sql = "UPDATE unidades SET codigo = ?, nome = ?, descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, unidade.getCodigo());
            stmt.setString(2, unidade.getNome());
            stmt.setString(3, unidade.getDescricao());
            stmt.setInt(4, unidade.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Unidade atualizada com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar unidade: " + e.getMessage());
        }
    }

   
    public void remover(int id) {
        String sql = "DELETE FROM unidades WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Unidade removida com sucesso!");

        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(null, "Erro ao remover unidade: " + e.getMessage());
        }
    }
}
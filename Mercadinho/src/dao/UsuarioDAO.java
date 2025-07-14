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
import model.Usuario;
import util.ConnectionFactory;

public class UsuarioDAO {

  
    public Usuario login(String username, String senha) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND senha = ? AND ativo = 1";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setTipo(rs.getString("tipo"));
                    usuario.setAtivo(rs.getBoolean("ativo"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar fazer login: " + e.getMessage());
        }
     
        return null;
    }


    public void adicionar(Usuario usuario) {
        String sql = "INSERT INTO usuarios(username, nome, senha, tipo, ativo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipo());
            stmt.setBoolean(5, usuario.isAtivo());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário: " + e.getMessage());
        }
    }


    public List<Usuario> listar() {
        String sql = "SELECT id, username, nome, tipo, ativo FROM usuarios"; // Evita trafegar a senha
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setUsername(rs.getString("username"));
                usuario.setNome(rs.getString("nome"));
                usuario.setTipo(rs.getString("tipo"));
                usuario.setAtivo(rs.getBoolean("ativo"));
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }

   
    public void atualizar(Usuario usuario) {
      
        String sql = "UPDATE usuarios SET username = ?, nome = ?, tipo = ?, ativo = ? WHERE id = ?";
        
        
        if (usuario.getSenha() != null && !usuario.getSenha().trim().isEmpty()) {
            sql = "UPDATE usuarios SET username = ?, nome = ?, senha = ?, tipo = ?, ativo = ? WHERE id = ?";
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getNome());

            if (usuario.getSenha() != null && !usuario.getSenha().trim().isEmpty()) {
                stmt.setString(3, usuario.getSenha());
                stmt.setString(4, usuario.getTipo());
                stmt.setBoolean(5, usuario.isAtivo());
                stmt.setInt(6, usuario.getId());
            } else {
                stmt.setString(3, usuario.getTipo());
                stmt.setBoolean(4, usuario.isAtivo());
                stmt.setInt(5, usuario.getId());
            }

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar usuário: " + e.getMessage());
        }
    }


    public void remover(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover usuário: " + e.getMessage());
        }
    }
}
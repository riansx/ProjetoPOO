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
import model.Cliente;
import util.ConnectionFactory;

public class ClienteDAO {


    public void adicionar(Cliente cliente) {
        String sql = "INSERT INTO clientes(nome, cpf_cnpj, telefone, endereco, cidade, cep, uf) VALUES (?, ?, ?, ?, ?, ?, ?)";

    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

        
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpfCnpj());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getCidade());
            stmt.setString(6, cliente.getCep());
            stmt.setString(7, cliente.getUf());

            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente: " + e.getMessage());
        }
    }


    public List<Cliente> listar() {
        String sql = "SELECT * FROM clientes";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

           
            while (rs.next()) {
                
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpfCnpj(rs.getString("cpf_cnpj"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setCep(rs.getString("cep"));
                cliente.setUf(rs.getString("uf"));

                
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }


    public void atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, cpf_cnpj = ?, telefone = ?, endereco = ?, cidade = ?, cep = ?, uf = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpfCnpj());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getCidade());
            stmt.setString(6, cliente.getCep());
            stmt.setString(7, cliente.getUf());
            stmt.setInt(8, cliente.getId()); // O ID é usado na cláusula WHERE para identificar o registro a ser atualizado.

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar cliente: " + e.getMessage());
        }
    }

 
    public void remover(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); 
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover cliente: " + e.getMessage());
        }
    }
}
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
import model.Produto;
import model.Unidade;
import util.ConnectionFactory;

public class ProdutoDAO {


    public void adicionar(Produto produto) {
        String sql = "INSERT INTO produtos(codigo, nome, preco, estoque, data_ultima_compra, unidade_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getCodigo());
            stmt.setString(2, produto.getNome());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            
            if (produto.getDataUltimaCompra() != null) {
                stmt.setDate(5, new java.sql.Date(produto.getDataUltimaCompra().getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            
            stmt.setInt(6, produto.getUnidade().getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        }
    }


    public List<Produto> listar() {
        String sql = "SELECT p.id as produto_id, p.codigo, p.nome, p.preco, p.estoque, p.data_ultima_compra, " +
                     "u.id as unidade_id, u.codigo as unidade_codigo, u.nome as unidade_nome, u.descricao as unidade_descricao " +
                     "FROM produtos as p INNER JOIN unidades as u ON p.unidade_id = u.id";
        
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Unidade unidade = new Unidade();
                unidade.setId(rs.getInt("unidade_id"));
                unidade.setCodigo(rs.getString("unidade_codigo"));
                unidade.setNome(rs.getString("unidade_nome"));
                unidade.setDescricao(rs.getString("unidade_descricao"));
                Produto produto = new Produto();
                produto.setId(rs.getInt("produto_id"));
                produto.setCodigo(rs.getString("codigo"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getBigDecimal("preco"));
                produto.setEstoque(rs.getInt("estoque"));
                produto.setDataUltimaCompra(rs.getDate("data_ultima_compra"));
                produto.setUnidade(unidade);
                produtos.add(produto);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

  
    public void atualizar(Produto produto) {
        String sql = "UPDATE produtos SET codigo = ?, nome = ?, preco = ?, estoque = ?, data_ultima_compra = ?, unidade_id = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getCodigo());
            stmt.setString(2, produto.getNome());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setDate(5, new java.sql.Date(produto.getDataUltimaCompra().getTime()));
            stmt.setInt(6, produto.getUnidade().getId());
            stmt.setInt(7, produto.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + e.getMessage());
        }
    }

   
    public void remover(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover produto: " + e.getMessage());
        }
    }
}


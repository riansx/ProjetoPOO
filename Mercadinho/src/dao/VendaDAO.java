/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import model.Venda;
import util.ConnectionFactory;
/**
 *
 * @author Administrator
 */

public class VendaDAO {

    public void adicionar(Venda venda) {
        String sql = "INSERT INTO vendas (data_venda, cliente_id, forma_pagamento, valor_total) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, new java.sql.Date(venda.getDataVenda().getTime()));
            stmt.setInt(2, venda.getCliente().getId());
            stmt.setString(3, venda.getFormaPagamento());
            stmt.setBigDecimal(4, venda.getTotal());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                venda.setId(rs.getInt(1)); // para usar depois na ItemVendaDAO
            }

            JOptionPane.showMessageDialog(null, "Venda registrada com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar venda: " + e.getMessage());
        }
    }

    public List<Venda> listar() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM vendas";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setDataVenda(rs.getDate("data_venda"));
                // Aqui você pode buscar o cliente por ID se quiser mostrar detalhes
                venda.setFormaPagamento(rs.getString("forma_pagamento"));
                venda.setTotal(rs.getBigDecimal("valor_total"));
                vendas.add(venda);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar vendas: " + e.getMessage());
        }
        return vendas;
    }
}

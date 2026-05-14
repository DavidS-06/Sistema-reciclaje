package com.almacen.ventanas;

import javax.swing.*;
import java.awt.*;
import com.almacen.ConexionPostgreSQL;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class VentanaRetiro extends JFrame {

    private final VentanaIndex ventanaAnterior;
    private final long idUsuario;

    private final int ANCHO_VENTANA = 500;
    private final int ALTO_VENTANA = 420;
    private final int ANCHO_CAMPO = 320;
    private final int ALTO_CAMPO = 32;
    private final int ANCHO_BOTON = 140;
    private final int ALTO_BOTON = 36;

    private final Dimension TAM_VENTANA = new Dimension(ANCHO_VENTANA, ALTO_VENTANA);
    private final Dimension TAM_CAMPO = new Dimension(ANCHO_CAMPO, ALTO_CAMPO);
    private final Dimension TAM_BOTON = new Dimension(ANCHO_BOTON, ALTO_BOTON);

    public VentanaRetiro(VentanaIndex ventanaAnterior, long idUsuario) {
        this.ventanaAnterior = ventanaAnterior;
        this.idUsuario = idUsuario;
        initUI();
    }

    private void initUI() {
        setTitle("Registrar Material");
        setSize(TAM_VENTANA);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Colores.GRIS_CLARO_FONDO);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 6, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Encabezado
        JPanel header = new JPanel();
        header.setBackground(Colores.AZUL_PRINCIPAL);
        header.setPreferredSize(new Dimension(360, 45));
        header.setLayout(new BorderLayout());
        JLabel lblHeader = new JLabel("REGISTRAR MATERIAL", SwingConstants.CENTER);
        lblHeader.setFont(Fuentes.TITULO);
        lblHeader.setForeground(Colores.BLANCO);
        header.add(lblHeader, BorderLayout.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(header, gbc);

        JLabel lblInstruccion = new JLabel("LLENAR LOS SIGUIENTES DATOS", SwingConstants.CENTER);
        lblInstruccion.setFont(Fuentes.CUERPO);
        lblInstruccion.setForeground(Colores.GRIS_TEXTO);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(lblInstruccion, gbc);

        //ID de la publicacion
        JLabel lblIdMat = new JLabel("Ingresa la ID de la publicacion:");
        lblIdMat.setFont(Fuentes.CUERPO);
        lblIdMat.setForeground(Colores.GRIS_TEXTO);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(lblIdMat, gbc);

        JTextField txtIdPublicacion = new JTextField();
        estiloCampo(txtIdPublicacion);
        txtIdPublicacion.setPreferredSize(TAM_CAMPO);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(txtIdPublicacion, gbc);

        //Cantidad de material retirado
        JLabel lblCant = new JLabel("Ingresa la cantidad de material a retirar:");
        lblCant.setFont(Fuentes.CUERPO);
        lblCant.setForeground(Colores.GRIS_TEXTO);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(lblCant, gbc);

        JTextField txtCantidad = new JTextField();
        estiloCampo(txtCantidad);
        txtCantidad.setPreferredSize(TAM_CAMPO);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(txtCantidad, gbc);

        //Botón retirar
        JButton btnRetirar = new JButton("RETIRAR");
        btnRetirar.setBackground(Colores.TURQUESA);
        btnRetirar.setForeground(Colores.BLANCO);
        btnRetirar.setFont(Fuentes.BOTON);
        btnRetirar.setPreferredSize(TAM_BOTON);
        btnRetirar.setFocusPainted(false);
        btnRetirar.addActionListener(e -> {
            try {
                long idPublicacion = Long.parseLong(txtIdPublicacion.getText().trim());
                float cantidadMaterialRetirado = Float.parseFloat(txtCantidad.getText().trim());
                retirarMaterial(idPublicacion, cantidadMaterialRetirado);
                //Limpiar campos
                txtIdPublicacion.setText("");
                txtCantidad.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Debe ingresar datos validos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.insets = new Insets(15, 20, 8, 20);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(btnRetirar, gbc);

        //Boton ver publicaciones
        JButton btnVer = new JButton("VER PUBLICACIONES");
        btnVer.setBackground(Colores.TURQUESA);
        btnVer.setForeground(Colores.BLANCO);
        btnVer.setFont(Fuentes.BOTON);
        btnVer.setPreferredSize(TAM_BOTON);
        btnVer.setFocusPainted(false);
        btnVer.addActionListener(e -> {
            DefaultTableModel modeloTabla = new DefaultTableModel();
            modeloTabla.addColumn("ID publicacion");
            modeloTabla.addColumn("Material");
            modeloTabla.addColumn("Usuario");
            modeloTabla.addColumn("Fecha");
            modeloTabla.addColumn("Lugar");
            modeloTabla.addColumn("Cantidad");
            modeloTabla.addColumn("Unidad");
            modeloTabla.addColumn("Descripcion");
            modeloTabla.addColumn("Estado");
            modeloTabla = consultarMateriales(modeloTabla);

            JTable tablaPublicaciones = new JTable(modeloTabla);
            tablaPublicaciones.setEnabled(false);
            JScrollPane panelPublicaciones = new JScrollPane(tablaPublicaciones);
            JOptionPane.showMessageDialog(null, panelPublicaciones, "PUBLICACIONES", JOptionPane.PLAIN_MESSAGE);
        });
        gbc.insets = new Insets(5, 20, 15, 20);
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        add(btnVer, gbc);

        //Boton regresar
        JButton btnRegresar = new JButton("REGRESAR AL MENÚ");
        btnRegresar.setBackground(Colores.GRIS_MEDIO);
        btnRegresar.setForeground(Colores.BLANCO);
        btnRegresar.setFont(Fuentes.BOTON);
        btnRegresar.setPreferredSize(TAM_BOTON);
        btnRegresar.setFocusPainted(false);
        btnRegresar.addActionListener(e -> {
            new VentanaMenu(this.ventanaAnterior, this.idUsuario).setVisible(true);
            dispose();
        });
        gbc.insets = new Insets(5, 20, 15, 20);
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        add(btnRegresar, gbc);
    }

    private void estiloCampo(JComponent c) {
        c.setFont(Fuentes.CUERPO);
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.BORDE, 1, true),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    private DefaultTableModel consultarMateriales(DefaultTableModel modeloTabla) {
        //Conexion BD
        Connection conn = null;
        PreparedStatement stmtPublis = null;
        ResultSet resultadosPublis = null;
        DefaultTableModel modelo = modeloTabla;

        try {
            conn = ConexionPostgreSQL.getConexion();
            String sql = "select id_publicacion, nombre, id_usuario, fecha, lugar, cantidad, unidad, descripcion, estado "
                    + "from materiales join publicaciones on materiales.id = publicaciones.id_material";
            stmtPublis = conn.prepareStatement(sql);
            resultadosPublis = stmtPublis.executeQuery();
            while (resultadosPublis.next()) {
                modeloTabla.addRow(new Object[]{
                    resultadosPublis.getLong("id_publicacion"),
                    resultadosPublis.getString("nombre"),
                    resultadosPublis.getLong("id_usuario"),
                    resultadosPublis.getDate("fecha"),
                    resultadosPublis.getString("lugar"),
                    resultadosPublis.getFloat("cantidad"),
                    resultadosPublis.getString("unidad"),
                    resultadosPublis.getString("descripcion"),
                    resultadosPublis.getString("estado")
                });
            }
            conn.close();
            return modelo;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return null;
        }
    }

    private void retirarMaterial(Long idPublicacion, float cantidadMaterialRetirado) {
        //Conexion BD
        Connection conn = null;
        PreparedStatement stmtProcesos = null;
        ResultSet validarEstado = null;

        try {
            conn = ConexionPostgreSQL.getConexion();
            String sqlValidarEstado = "select cantidad from publicaciones where id_publicacion = ?";
            stmtProcesos = conn.prepareStatement(sqlValidarEstado);
            stmtProcesos.setLong(1, idPublicacion);
            validarEstado = stmtProcesos.executeQuery();
            if (validarEstado.next()) {
                if (validarEstado.getFloat("cantidad") == 0) {
                    JOptionPane.showMessageDialog(null, "El material no se encuentra disponible.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    conn.close();
                    return;
                }
                float cantidadAnterior = validarEstado.getFloat("cantidad");
                float cantidadNueva = cantidadAnterior - cantidadMaterialRetirado;
                if (cantidadNueva < 0) {
                    JOptionPane.showMessageDialog(null, "La cantidad de material retirado es superior a la cantidad disponible.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    conn.close();
                    return;
                }
                String sqlActualizarDatos = "update publicaciones set cantidad = ? where id_publicacion = ?";
                stmtProcesos = conn.prepareStatement(sqlActualizarDatos);
                stmtProcesos.setFloat(1, cantidadNueva);
                stmtProcesos.setLong(2, idPublicacion);
                int filasActualizadas = stmtProcesos.executeUpdate();
                if (filasActualizadas > 0) {
                    JOptionPane.showMessageDialog(null, "Retiro registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                }
                if (cantidadNueva == 0) {
                    sqlActualizarDatos = "update publicaciones set estado = 'Agotado' where id_publicacion = ?";
                    stmtProcesos = conn.prepareStatement(sqlActualizarDatos);
                    stmtProcesos.setLong(1, idPublicacion);
                    stmtProcesos.execute();
                }
                conn.close();
            } else {
                JOptionPane.showMessageDialog(null, "No hay ninguna publicación con dicho ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

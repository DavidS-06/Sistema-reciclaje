package com.almacen.ventanas;

import javax.swing.*;
import java.awt.*;
import com.almacen.ConexionPostgreSQL;
import com.almacen.HashMD5;
import java.sql.*;

public class VentanaCrearCuenta extends JFrame {

    private final int ANCHO_VENTANA = 500;
    private final int ALTO_VENTANA = 480;

    public VentanaCrearCuenta(Window ventanaAnterior) {
        setTitle("Crear Cuenta");
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setMinimumSize(new Dimension(ANCHO_VENTANA, ALTO_VENTANA));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(ventanaAnterior);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(245, 247, 250));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título de la ventana
        JLabel lblTitulo = new JLabel("Crear Cuenta");
        lblTitulo.setFont(Fuentes.TITULO);
        lblTitulo.setForeground(Colores.AZUL_PRINCIPAL);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 15, 0);
        panelPrincipal.add(lblTitulo, gbc);
        gbc.insets = new Insets(8, 10, 8, 10);

        //Matricula
        JLabel lblMatricula = new JLabel("Matrícula");
        lblMatricula.setFont(Fuentes.SUBTITULO);
        lblMatricula.setForeground(Colores.AZUL_PRINCIPAL);
        JTextField txtMatricula = crearCampoTexto();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        panelPrincipal.add(lblMatricula, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 0.8;
        panelPrincipal.add(txtMatricula, gbc);

        //Nombre
        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setFont(Fuentes.SUBTITULO);
        lblNombre.setForeground(Colores.AZUL_PRINCIPAL);
        JTextField txtNombre = crearCampoTexto();

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        panelPrincipal.add(lblNombre, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 0.8;
        panelPrincipal.add(txtNombre, gbc);

        //Apellidos
        JLabel lblApellidoP = new JLabel("Apellido P:");
        lblApellidoP.setFont(Fuentes.SUBTITULO);
        lblApellidoP.setForeground(Colores.AZUL_PRINCIPAL);
        JTextField txtApellidoP = crearCampoTexto();

        JLabel lblApellidoM = new JLabel("Apellido M:");
        lblApellidoM.setFont(Fuentes.SUBTITULO);
        lblApellidoM.setForeground(Colores.AZUL_PRINCIPAL);
        JTextField txtApellidoM = crearCampoTexto();

        //Apellido Paterno
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.15;
        panelPrincipal.add(lblApellidoP, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.30;
        panelPrincipal.add(txtApellidoP, gbc);

        //Apellido Materno
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.15;
        panelPrincipal.add(lblApellidoM, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.30;
        panelPrincipal.add(txtApellidoM, gbc);

        //Division
        JLabel lblDivision = new JLabel("División");
        lblDivision.setFont(Fuentes.SUBTITULO);
        lblDivision.setForeground(Colores.AZUL_PRINCIPAL);

        String[] opcionesDiv = {"Ing. en TICS", "Ing. Industrial", "Ing. Química", "Ing. Mecatrónica", "Otra"};
        JComboBox<String> cbDivision = crearComboBox(opcionesDiv);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        panelPrincipal.add(lblDivision, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weightx = 0.8;
        panelPrincipal.add(cbDivision, gbc);

        //Rol
        JLabel lblRol = new JLabel("Rol");
        lblRol.setFont(Fuentes.SUBTITULO);
        lblRol.setForeground(Colores.AZUL_PRINCIPAL);

        String[] opcionesRol = {"Estudiante", "Docente"};
        JComboBox<String> cbRol = crearComboBox(opcionesRol);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        panelPrincipal.add(lblRol, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.weightx = 0.8;
        panelPrincipal.add(cbRol, gbc);

        //Contraseña
        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(Fuentes.SUBTITULO);
        lblContrasena.setForeground(Colores.AZUL_PRINCIPAL);
        JPasswordField txtContrasena = crearCampoContrasena();

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        panelPrincipal.add(lblContrasena, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.weightx = 0.8;
        panelPrincipal.add(txtContrasena, gbc);

        //Botones
        JButton btnGuardar = new JButton("Guardar");
        estiloBotonPrincipal(btnGuardar);
        JButton btnCancelar = new JButton("Cancelar");
        estiloBotonSecundario(btnCancelar);

        btnGuardar.addActionListener(e -> {
            try {
                // Obtener el texto de los campos y quitar espacios en blanco al inicio y final
                long matricula = Long.parseLong(txtMatricula.getText().trim());
                String nombre = txtNombre.getText().trim();
                String apellidoP = txtApellidoP.getText().trim();
                String apellidoM = txtApellidoM.getText().trim();
                String division = cbDivision.getSelectedItem() != null ? cbDivision.getSelectedItem().toString() : "";
                String rol = cbRol.getSelectedItem() != null ? cbRol.getSelectedItem().toString() : "";
                String contrasena = HashMD5.generarMD5Hash(new String(txtContrasena.getPassword()));

                // Verificar que no haya campos vacíos
                if (nombre.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty()
                        || division.isEmpty() || rol.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos correctamente.", "Aviso", JOptionPane.WARNING_MESSAGE);

                } else {
                    //Conexion a BD
                    Connection conn = null;
                    PreparedStatement stmtUsuario = null;
                    PreparedStatement stmtCuenta = null;

                    try {
                        //Conectar
                        conn = ConexionPostgreSQL.getConexion();

                        if (conn != null) {
                            //Preparar sentencia
                            String sql = "INSERT INTO usuarios (matricula, nombre, apellido_paterno, apellido_materno, division, rol) "
                                    + "VALUES (?, ?, ?, ?, ?, ?)";

                            stmtUsuario = conn.prepareStatement(sql);

                            stmtUsuario.setLong(1, matricula);
                            stmtUsuario.setString(2, nombre);
                            stmtUsuario.setString(3, apellidoP);
                            stmtUsuario.setString(4, apellidoM);
                            stmtUsuario.setString(5, division);
                            stmtUsuario.setString(6, rol);

                            String sql2 = "INSERT INTO cuentas (matricula, contrasenia) "
                                    + "VALUES (?, ?)";

                            stmtCuenta = conn.prepareStatement(sql2);

                            stmtCuenta.setLong(1, matricula);
                            stmtCuenta.setString(2, contrasena);

                            // Ejecutar inserción
                            int filasInsertadasUsuario = stmtUsuario.executeUpdate();
                            int filasInsertadasCuenta = stmtCuenta.executeUpdate();

                            if (filasInsertadasUsuario > 0 && filasInsertadasCuenta > 0) {
                                JOptionPane.showMessageDialog(null, "Datos guardados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                                // 🧹 Limpiar campos
                                txtMatricula.setText("");
                                txtNombre.setText("");
                                txtApellidoP.setText("");
                                txtApellidoM.setText("");
                                txtContrasena.setText("");
                                cbDivision.setSelectedIndex(0); // Reiniciar combobox
                                cbRol.setSelectedIndex(0);      // Reiniciar combobox
                            }
                            conn.close();
                        }
                    } catch (SQLException ex) {
                        // Si hay error de base de datos
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Debe ingresar datos validos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        );

        btnCancelar.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            dispose();
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(5, 0, 5, 0); //Menos espacio arriba de botones
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(panelBotones, gbc);

        add(panelPrincipal);
        setVisible(true);
    }

    private void estiloBotonPrincipal(JButton btn) {
        btn.setFont(Fuentes.BOTON);
        btn.setBackground(Colores.TURQUESA);
        btn.setForeground(Colores.BLANCO);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 35));
    }

    private void estiloBotonSecundario(JButton btn) {
        btn.setFont(Fuentes.BOTON);
        btn.setBackground(Colores.AZUL_PRINCIPAL);
        btn.setForeground(Colores.BLANCO);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 35));
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(Fuentes.CUERPO);
        campo.setBackground(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.AZUL_PRINCIPAL, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return campo;
    }

    private JPasswordField crearCampoContrasena() {
        JPasswordField campo = new JPasswordField();
        campo.setFont(Fuentes.CUERPO);
        campo.setBackground(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.AZUL_PRINCIPAL, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return campo;
    }

    private JComboBox<String> crearComboBox(String[] opciones) {
        JComboBox<String> combo = new JComboBox<>(opciones);
        combo.setFont(Fuentes.CUERPO);
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.AZUL_PRINCIPAL, 1, true),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        combo.setPreferredSize(new Dimension(120, 32));
        return combo;
    }
}

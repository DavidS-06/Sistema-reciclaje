package com.almacen.ventanas;

import javax.swing.*;
import java.awt.*;
import com.almacen.ConexionPostgreSQL;
import com.almacen.HashMD5;
import java.sql.*;

public class VentanaInicioSesion extends JFrame {

    private final VentanaIndex ventanaAnterior;

    private final int ANCHO_VENTANA = 500;
    private final int ALTO_VENTANA = 420;
    private final int ANCHO_CAMPO = 320;
    private final int ALTO_CAMPO = 32;
    private final int ANCHO_BOTON = 140;
    private final int ALTO_BOTON = 36;
    private final int ANCHO_APELLIDO = 150;
    private final int ALTO_APELLIDO = 32;
    private final Dimension TAM_VENTANA = new Dimension(ANCHO_VENTANA, ALTO_VENTANA);
    private final Dimension TAM_CAMPO = new Dimension(ANCHO_CAMPO, ALTO_CAMPO);
    private final Dimension TAM_BOTON = new Dimension(ANCHO_BOTON, ALTO_BOTON);
    private final Dimension TAM_APELLIDO = new Dimension(ANCHO_APELLIDO, ALTO_APELLIDO);

    public VentanaInicioSesion(VentanaIndex ventanaAnterior) {
        this.ventanaAnterior = ventanaAnterior;
        initUI();
    }

    private void initUI() {
        setTitle("Inicio de Sesión");
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Colores.GRIS_CLARO_FONDO);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 20, 6, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Encabezado
        JPanel header = new JPanel();
        header.setBackground(Colores.GRIS_MEDIO);
        header.setPreferredSize(new Dimension(360, 40));
        header.setLayout(new BorderLayout());
        JLabel lblHeader = new JLabel("INICIO DE SESIÓN", SwingConstants.CENTER);
        lblHeader.setFont(Fuentes.TITULO);
        lblHeader.setForeground(Colores.AZUL_PRINCIPAL);
        header.add(lblHeader);
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(header, gbc);

        //Numero de control
        JLabel lbl1 = new JLabel("N° de Control");
        lbl1.setFont(Fuentes.CUERPO);
        lbl1.setForeground(Colores.GRIS_TEXTO);
        gbc.gridy = 1;
        add(lbl1, gbc);

        JTextField txtControl = new JTextField();
        estiloCampo(txtControl);
        txtControl.setPreferredSize(TAM_CAMPO);
        gbc.gridy = 2;
        add(txtControl, gbc);

        //Contraseña
        JLabel lbl2 = new JLabel("Contraseña");
        lbl2.setFont(Fuentes.CUERPO);
        lbl2.setForeground(Colores.GRIS_TEXTO);
        gbc.gridy = 3;
        add(lbl2, gbc);

        JPanel pnlPass = new JPanel(new BorderLayout());
        pnlPass.setOpaque(false);
        pnlPass.setPreferredSize(TAM_CAMPO);

        JPasswordField txtContrasena = new JPasswordField();
        estiloCampo(txtContrasena);

        pnlPass.add(txtContrasena, BorderLayout.CENTER);
        gbc.gridy = 4;
        add(pnlPass, gbc);

        //Botón ingresar
        JButton btnIng = new JButton("INGRESAR");
        btnIng.setBackground(Colores.TURQUESA);
        btnIng.setForeground(Colores.BLANCO);
        btnIng.setFont(Fuentes.BOTON);
        btnIng.setPreferredSize(TAM_BOTON);
        btnIng.setFocusPainted(false);
        btnIng.addActionListener(e -> {
            try {
                String control = txtControl.getText().trim();
                if (control.length() < 9 || control.length() > 10 || txtControl.getText().isBlank() || txtContrasena.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(this, "Por favor completa todos los campos", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    Long numControl = Long.parseLong(control);
                    String contrasena = HashMD5.generarMD5Hash(new String(txtContrasena.getPassword()));

                    if (contrasena.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        iniciarSesion(numControl, contrasena);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Debe ingresar datos validos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.insets = new Insets(20, 20, 8, 20);
        gbc.gridy = 5;
        add(btnIng, gbc);

        //Botón regresar
        JButton btnRegresar = new JButton("REGRESAR AL INICIO");
        btnRegresar.setBackground(Colores.GRIS_MEDIO);
        btnRegresar.setForeground(Colores.BLANCO);
        btnRegresar.setFont(Fuentes.BOTON);
        btnRegresar.setPreferredSize(TAM_BOTON);
        btnRegresar.setFocusPainted(false);
        btnRegresar.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            dispose();
        });
        gbc.insets = new Insets(5, 20, 15, 20);
        gbc.gridy = 6;
        add(btnRegresar, gbc);
    }

    //Estilos compartidos
    private void estiloCampo(JComponent c) {
        c.setFont(Fuentes.CUERPO);
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.BORDE, 1, true),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    private void iniciarSesion(Long numControl, String contrasena) {
        //Conexion a BD
        Connection conn = null;
        PreparedStatement stmtIniciarSesion = null;
        ResultSet cuentaObtenida = null;
        
        Long numControlObtenido = null;
        String contrasenaObtenida = null;
        try {
            conn = ConexionPostgreSQL.getConexion();
            if (conn != null) {
                //Preparar sentencia
                String sql = "SELECT matricula, contrasenia FROM cuentas "
                        + "WHERE matricula = ? AND contrasenia = ?";

                stmtIniciarSesion = conn.prepareStatement(sql);

                stmtIniciarSesion.setLong(1, numControl);
                stmtIniciarSesion.setString(2, contrasena);

                cuentaObtenida = stmtIniciarSesion.executeQuery();
                while (cuentaObtenida.next()) {
                    numControlObtenido = cuentaObtenida.getLong("matricula");
                    contrasenaObtenida = cuentaObtenida.getString("contrasenia");
                }
                if (numControl.equals(numControlObtenido) && contrasena.equals(contrasenaObtenida)) {
                    new VentanaMenu(ventanaAnterior, numControl).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario y/o la contraseña son incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // Si hay error de base de datos
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

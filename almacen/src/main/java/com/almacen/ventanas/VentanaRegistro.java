package com.almacen.ventanas;

import javax.swing.*;
import java.awt.*;
import com.almacen.ConexionPostgreSQL;
import java.sql.*;

public class VentanaRegistro extends JFrame {

    private final VentanaIndex ventanaAnterior;
    private long idUsuario;

    private final int ANCHO_VENTANA = 500;
    private final int ALTO_VENTANA = 600;
    private final int ANCHO_CAMPO = 320;
    private final int ALTO_CAMPO = 32;
    private final int ANCHO_BOTON = 140;
    private final int ALTO_BOTON = 36;

    private final Dimension TAM_VENTANA = new Dimension(ANCHO_VENTANA, ALTO_VENTANA);
    private final Dimension TAM_CAMPO = new Dimension(ANCHO_CAMPO, ALTO_CAMPO);
    private final Dimension TAM_BOTON = new Dimension(ANCHO_BOTON, ALTO_BOTON);

    public VentanaRegistro(VentanaIndex ventanaAnterior, long idUsuario) {
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
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(header, gbc);

        JLabel lblInstruccion = new JLabel("LLENAR LOS SIGUIENTES DATOS", SwingConstants.CENTER);
        lblInstruccion.setFont(Fuentes.CUERPO);
        lblInstruccion.setForeground(Colores.GRIS_TEXTO);
        gbc.gridy = 1;
        add(lblInstruccion, gbc);

        //Tipo de Material
        JLabel lblTipo = new JLabel("¿Qué tipo de material es?");
        lblTipo.setFont(Fuentes.CUERPO);
        lblTipo.setForeground(Colores.GRIS_TEXTO);
        gbc.gridy = 2;
        add(lblTipo, gbc);

        String[] materiales = {"Cartón", "Plástico", "Útil escolar", "Ropa", "Material de higiene", "Material perdido"};
        JComboBox<String> cboTipoMaterial = new JComboBox<>(materiales);
        estiloCombo(cboTipoMaterial);
        cboTipoMaterial.setPreferredSize(TAM_CAMPO);
        gbc.gridy = 3;
        add(cboTipoMaterial, gbc);

        //Lugar
        JLabel lblLugar = new JLabel("Ingresa el lugar:");
        lblLugar.setFont(Fuentes.CUERPO);
        lblLugar.setForeground(Colores.GRIS_TEXTO);
        gbc.gridy = 4;
        add(lblLugar, gbc);

        JTextField txtLugar = new JTextField();
        estiloCampo(txtLugar);
        txtLugar.setPreferredSize(TAM_CAMPO);
        gbc.gridy = 5;
        add(txtLugar, gbc);

        //Cantidad
        JLabel lblCant = new JLabel("Ingresa la cantidad:");
        lblCant.setFont(Fuentes.CUERPO);
        lblCant.setForeground(Colores.GRIS_TEXTO);
        gbc.gridy = 6;
        add(lblCant, gbc);

        JTextField txtCantidad = new JTextField();
        estiloCampo(txtCantidad);
        txtCantidad.setPreferredSize(TAM_CAMPO);
        gbc.gridy = 7;
        add(txtCantidad, gbc);

        //Unidad
        JLabel lblUnid = new JLabel("Selecciona la unidad:");
        lblUnid.setFont(Fuentes.CUERPO);
        lblUnid.setForeground(Colores.GRIS_TEXTO);
        gbc.gridy = 8;
        add(lblUnid, gbc);

        String[] unidades = new String[]{"Piezas", "Gramos"};
        JComboBox<String> cboUnidad = new JComboBox<>(unidades);
        estiloCombo(cboUnidad);
        cboUnidad.setPreferredSize(TAM_CAMPO);
        gbc.gridy = 9;
        add(cboUnidad, gbc);

        //Descripción
        JLabel lblDesc = new JLabel("Descripción:");
        lblDesc.setFont(Fuentes.CUERPO);
        lblDesc.setForeground(Colores.GRIS_TEXTO);
        gbc.gridy = 10;
        add(lblDesc, gbc);

        JTextArea txtDescripcion = new JTextArea();
        estiloCampo(txtDescripcion);
        txtDescripcion.setPreferredSize(new Dimension(ANCHO_CAMPO, 50));
        gbc.gridy = 11;
        add(txtDescripcion, gbc);

        //Botón publicar
        JButton btnPublicar = new JButton("PUBLICAR");
        btnPublicar.setBackground(Colores.TURQUESA);
        btnPublicar.setForeground(Colores.BLANCO);
        btnPublicar.setFont(Fuentes.BOTON);
        btnPublicar.setPreferredSize(TAM_BOTON);
        btnPublicar.setFocusPainted(false);
        btnPublicar.addActionListener(e -> {

            try {
                //Obtener los datos de los campos de texto
                long idUsuarioPublicacion = this.idUsuario;
                int idMaterial = cboTipoMaterial.getSelectedIndex() + 1;
                Date fecha = new Date(
                        java.time.LocalDate.now().getYear() - 1900,
                        java.time.LocalDate.now().getMonthValue() - 1,
                        java.time.LocalDate.now().getDayOfMonth()
                );
                String lugar = txtLugar.getText().trim();
                float cantidad = Float.parseFloat(txtCantidad.getText().trim());
                String unidad = cboUnidad.getSelectedItem().toString().trim();
                String descripcion = txtDescripcion.getText().trim();
                String estado = "Disponible";

                if (lugar.isEmpty() || descripcion.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos correctamente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    publicarMaterial(idUsuarioPublicacion, idMaterial, fecha, lugar, cantidad, unidad, descripcion, estado);
                    //Reiniciar campos
                    cboTipoMaterial.setSelectedIndex(0);
                    txtLugar.setText("");
                    txtCantidad.setText("");
                    cboUnidad.setSelectedIndex(0);
                    txtDescripcion.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Debe ingresar datos validos.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        gbc.insets = new Insets(15, 20, 8, 20);
        gbc.gridy = 12;
        add(btnPublicar, gbc);

        //Regresar
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
        gbc.gridy = 13;
        add(btnRegresar, gbc);
    }

    //Estilos
    private void estiloCampo(JComponent c) {
        c.setFont(Fuentes.CUERPO);
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.BORDE, 1, true),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    private void estiloCombo(JComboBox<?> cb) {
        cb.setFont(Fuentes.CUERPO);
        cb.setBackground(Colores.BLANCO);
        cb.setBorder(BorderFactory.createLineBorder(Colores.BORDE, 1, true));
    }

    private void publicarMaterial(Long idUsuarioPublicacion, int idMaterial, Date fecha, String lugar, float cantidad, String unidad, String descripcion, String estado) {
        //Conexion a BD
        Connection conn = null;
        PreparedStatement stmtPublicar = null;

        try {
            //Conectar
            conn = ConexionPostgreSQL.getConexion();

            if (conn != null) {
                String sql = "INSERT INTO publicaciones (id_usuario, id_material, fecha, lugar, cantidad, unidad, descripcion, estado) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                stmtPublicar = conn.prepareStatement(sql);
                stmtPublicar.setLong(1, idUsuarioPublicacion);
                stmtPublicar.setInt(2, idMaterial);
                stmtPublicar.setDate(3, fecha);
                stmtPublicar.setString(4, lugar);
                stmtPublicar.setFloat(5, cantidad);
                stmtPublicar.setString(6, unidad);
                stmtPublicar.setString(7, descripcion);
                stmtPublicar.setString(8, estado);

                int filasInsertadas = stmtPublicar.executeUpdate();

                if (filasInsertadas > 0) {
                    JOptionPane.showMessageDialog(null, "Publicacion realizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // Si hay error de base de datos
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

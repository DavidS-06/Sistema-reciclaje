package com.almacen.ventanas;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu extends JFrame {

    private final VentanaIndex ventanaAnterior;
    private long idUsuario;

    private final int ANCHO_VENTANA = 500;
    private final int ALTO_VENTANA = 420;
    private final int ANCHO_BOTON = 140;
    private final int ALTO_BOTON = 36;

    private final Dimension TAM_VENTANA = new Dimension(ANCHO_VENTANA, ALTO_VENTANA);
    private final Dimension TAM_BOTON = new Dimension(ANCHO_BOTON, ALTO_BOTON);

    public VentanaMenu(VentanaIndex ventanaAnterior, long idUsuario) {
        this.ventanaAnterior = ventanaAnterior;
        this.idUsuario = idUsuario;
        initUI();
    }

    private void initUI() {
        setTitle("Menú Principal");
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Colores.GRIS_CLARO_FONDO);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 20, 12, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel header = new JPanel();
        header.setBackground(Colores.AZUL_PRINCIPAL);
        header.setPreferredSize(new Dimension(360, 45));
        header.setLayout(new BorderLayout());
        JLabel lblHeader = new JLabel("REGISTRAR / RETIRAR", SwingConstants.CENTER);
        lblHeader.setFont(Fuentes.TITULO);
        lblHeader.setForeground(Colores.BLANCO);
        header.add(lblHeader, BorderLayout.CENTER);
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(header, gbc);

        //Titulo
        JLabel lblTitulo = new JLabel("¿Qué deseas hacer?", SwingConstants.CENTER);
        lblTitulo.setFont(Fuentes.SUBTITULO);
        lblTitulo.setForeground(Colores.AZUL_PRINCIPAL);
        gbc.gridy = 1;
        add(lblTitulo, gbc);

        // Botón Publicar material
        JButton btnPublicarMaterial = new JButton("PUBLICAR MATERIAL");
        estiloBotonPrincipal(btnPublicarMaterial);
        btnPublicarMaterial.setPreferredSize(TAM_BOTON);
        btnPublicarMaterial.addActionListener(e -> abrirRegistrar());
        gbc.gridy = 2;
        add(btnPublicarMaterial, gbc);

        // Botón Retirar
        JButton btnRetirar = new JButton("RETIRAR MATERIAL");
        estiloBotonSecundario(btnRetirar);
        btnRetirar.setPreferredSize(TAM_BOTON);
        btnRetirar.addActionListener(e -> abrirRetirar());
        gbc.gridy = 3;
        add(btnRetirar, gbc);

        // Botón Regresar
        JButton btnRegresar = new JButton("REGRESAR AL INICIO");
        estiloBotonGris(btnRegresar);
        btnRegresar.setPreferredSize(TAM_BOTON);
        btnRegresar.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            dispose();
        });
        gbc.insets = new Insets(20, 20, 10, 20);
        gbc.gridy = 4;
        add(btnRegresar, gbc);
    }

    private void estiloBotonPrincipal(JButton btn) {
        btn.setFont(Fuentes.BOTON);
        btn.setBackground(Colores.TURQUESA);
        btn.setForeground(Colores.BLANCO);
        btn.setFocusPainted(false);
    }

    private void estiloBotonSecundario(JButton btn) {
        btn.setFont(Fuentes.BOTON);
        btn.setBackground(Colores.AZUL_PRINCIPAL);
        btn.setForeground(Colores.BLANCO);
        btn.setFocusPainted(false);
    }

    private void estiloBotonGris(JButton btn) {
        btn.setFont(Fuentes.BOTON);
        btn.setBackground(Colores.GRIS_MEDIO);
        btn.setForeground(Colores.BLANCO);
        btn.setFocusPainted(false);
    }

    private void abrirRegistrar() {
        new VentanaRegistro(this.ventanaAnterior, this.idUsuario).setVisible(true);
        this.dispose();
    }

    private void abrirRetirar() {
        new VentanaRetiro(this.ventanaAnterior, this.idUsuario).setVisible(true);
        this.dispose();
    }
}

package com.almacen.ventanas;

import javax.swing.*;
import java.awt.*;

public class VentanaIndex extends JFrame {

    private final int ANCHO_VENTANA = 500;
    private final int ALTO_VENTANA = 420;
    private final int ANCHO_BOTON = 140;
    private final int ALTO_BOTON = 36;

    private final Dimension TAM_VENTANA = new Dimension(ANCHO_VENTANA, ALTO_VENTANA);
    private final Dimension TAM_BOTON = new Dimension(ANCHO_BOTON, ALTO_BOTON);

    public VentanaIndex() {
        initUI();
    }

    private void initUI() {
        //Ajustar ventana
        setTitle("Almacén de Materiales");
        setSize(TAM_VENTANA);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        //Layout con GridBag
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(25, 20, 15, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Título ventana
        JLabel lblTitulo = new JLabel("ALMACÉN DE MATERIALES", SwingConstants.CENTER);
        lblTitulo.setFont(Fuentes.TITULO);
        lblTitulo.setForeground(Colores.AZUL_PRINCIPAL);
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        //Botón crear cuenta
        JButton btnCrearCuenta = new JButton("CREAR CUENTA");
        estiloBotonPrincipal(btnCrearCuenta);
        btnCrearCuenta.setPreferredSize(TAM_BOTON);
        btnCrearCuenta.addActionListener(e -> abrirCrearCuenta());
        gbc.gridy = 1;
        add(btnCrearCuenta, gbc);

        //Botón iniciar sesión
        JButton btnIniciarSesion = new JButton("INICIAR SESIÓN");
        estiloBotonSecundario(btnIniciarSesion);
        btnIniciarSesion.setPreferredSize(TAM_BOTON);
        btnIniciarSesion.addActionListener(e -> abrirInicioSesion());
        gbc.gridy = 2;
        add(btnIniciarSesion, gbc);
    }

    private void estiloBotonPrincipal(JButton btn) {
        btn.setFont(Fuentes.BOTON);
        btn.setBackground(Colores.TURQUESA);
        btn.setForeground(Colores.BLANCO);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setFocusPainted(false);
    }

    private void estiloBotonSecundario(JButton btn) {
        btn.setFont(Fuentes.BOTON);
        btn.setBackground(Colores.AZUL_PRINCIPAL);
        btn.setForeground(Colores.BLANCO);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setFocusPainted(false);
    }

    private void abrirCrearCuenta() {
        new VentanaCrearCuenta(this).setVisible(true);
        this.dispose();
    }

    private void abrirInicioSesion() {
        new VentanaInicioSesion(this).setVisible(true);
        this.dispose();
    }
}

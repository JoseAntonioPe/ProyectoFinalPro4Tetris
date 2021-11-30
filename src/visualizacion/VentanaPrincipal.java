package visualizacion;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import javafx.embed.swing.JFXPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import modelos.Reproductor;
import tetris.Tetris;

public class VentanaPrincipal extends javax.swing.JFrame {

    public VentanaPrincipal() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("TETRIS 99");
        setResizable(false);
        //sonido del tetris
        new JFXPanel();
        Reproductor.obtenerReproductor().reproducirFondo(Reproductor.FONDO_INTRO, true);
        //fondo de pantalla
        ((JPanel) getContentPane()).setOpaque(false);
        ImageIcon uno = new ImageIcon(this.getClass().getResource("/imagenes/Fondo1.gif"));
        JLabel fondo = new JLabel();
        fondo.setIcon(uno);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, uno.getIconWidth(), uno.getIconHeight());
        //fondo de los botones
        ImageIcon botonSinglePlayer = new ImageIcon(this.getClass().getResource("/imagenes/botones-single.png"));
        ImageIcon botonMultiPlayer = new ImageIcon(this.getClass().getResource("/imagenes/botones-multi.png"));
        jBotonPartidaSolitario.setIcon(botonSinglePlayer);
        jBotonPartidaMultijugador.setIcon(botonMultiPlayer);
        //cambia forma del puntero
        jBotonPartidaSolitario.setCursor(new Cursor(HAND_CURSOR));
        jBotonPartidaMultijugador.setCursor(new Cursor(HAND_CURSOR));
        
    }

    //icono del juego
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("imagenes/icono.jpeg"));

        return retValue;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBotonPartidaMultijugador = new javax.swing.JButton();
        jBotonPartidaSolitario = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jBotonPartidaMultijugador.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jBotonPartidaMultijugador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBotonPartidaMultijugadorMouseEntered(evt);
            }
        });
        jBotonPartidaMultijugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonPartidaMultijugadorActionPerformed(evt);
            }
        });

        jBotonPartidaSolitario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBotonPartidaSolitarioMouseEntered(evt);
            }
        });
        jBotonPartidaSolitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonPartidaSolitarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBotonPartidaMultijugador, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBotonPartidaSolitario, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 132, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(241, 241, 241)
                .addComponent(jBotonPartidaSolitario, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(jBotonPartidaMultijugador, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(273, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBotonPartidaMultijugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonPartidaMultijugadorActionPerformed
        //sonido del boton
        new JFXPanel();
        Reproductor.obtenerReproductor().reproducirSonido(Reproductor.SONIDO_BOTON_CLICK2);
        VentanaMenuMultijugador ventanaMenuMultijugador = new VentanaMenuMultijugador();
        ventanaMenuMultijugador.setVisible(true);
    }//GEN-LAST:event_jBotonPartidaMultijugadorActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void jBotonPartidaMultijugadorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBotonPartidaMultijugadorMouseEntered
        //sonido del boton
        new JFXPanel();
        Reproductor.obtenerReproductor().reproducirSonido(Reproductor.SONIDO_BOTON_HOVER);
    }//GEN-LAST:event_jBotonPartidaMultijugadorMouseEntered

    private void jBotonPartidaSolitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonPartidaSolitarioActionPerformed
        //sonido del boton 
        new JFXPanel();
        Reproductor.obtenerReproductor().reproducirSonido(Reproductor.SONIDO_BOTON_CLICK);

        //inicio del juego
        Tetris tetris = new Tetris();
        tetris.setLocationRelativeTo(null);
        tetris.abrirVentana(this);
        tetris.setVisible(true);        tetris.setVisible(true);        tetris.setVisible(true);    }//GEN-LAST:event_jBotonPartidaSolitarioActionPerformed

    private void jBotonPartidaSolitarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBotonPartidaSolitarioMouseEntered
        //sonido del boton
        new JFXPanel();
        Reproductor.obtenerReproductor().reproducirSonido(Reproductor.SONIDO_BOTON_HOVER);
        Reproductor.obtenerReproductor().reproducirSonido(Reproductor.SONIDO_BOTON_HOVER);    }//GEN-LAST:event_jBotonPartidaSolitarioMouseEntered

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBotonPartidaMultijugador;
    private javax.swing.JButton jBotonPartidaSolitario;
    // End of variables declaration//GEN-END:variables

}

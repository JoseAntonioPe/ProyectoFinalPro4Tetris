
package visualizacion;

import conexion.Cliente;
import conexion.Jugador;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import tetris.TetrisMultiplayer;


public class Cargando extends javax.swing.JDialog {

   
    public Cargando(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        ((JPanel) getContentPane()).setOpaque(false);
        ImageIcon gif = new ImageIcon(getClass().getResource("/imagenes/cargando.gif"));
        JLabel fondo = new JLabel();
        fondo.setIcon(gif);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, gif.getIconWidth(), gif.getIconHeight());

    }

    public void registrarse(Jugador jugador) {
        try {
            System.out.println(jugador.obtenerNombre());
            new Cliente().registrarse(jugador);
            buscarPartida(jugador);
        } catch (Exception e) {
        }
    }

    public void buscarPartida(Jugador jugador) {
        Runnable run = () -> {
            try {
                while (true) {                    
                    Thread.sleep(500);
                     
                   String estado = new Cliente().obtenerEstado();
                    System.out.println("estado: "+estado);
                    if(new Cliente().obtenerEstado().equals("true")) {
                        break;
                    }
                }
                TetrisMultiplayer multijugador = new TetrisMultiplayer();
                
                multijugador.setLocationRelativeTo(null);
                multijugador.setAlwaysOnTop(true);
                multijugador.setJugador(jugador);
                multijugador.setVisible(true);
                dispose();
                multijugador.obtenerTableros();
            } catch (Exception e) {
                
            }
        };
        Thread hilo = new Thread(run);
        hilo.start();

    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JBoton_Cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JBoton_Cancelar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        JBoton_Cancelar.setText("Cancelar");
        JBoton_Cancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JBoton_CancelarMouseClicked(evt);
            }
        });
        JBoton_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBoton_CancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(JBoton_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(176, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(204, Short.MAX_VALUE)
                .addComponent(JBoton_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JBoton_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBoton_CancelarActionPerformed
        dispose();
    }//GEN-LAST:event_JBoton_CancelarActionPerformed

    private void JBoton_CancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JBoton_CancelarMouseClicked

    }//GEN-LAST:event_JBoton_CancelarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cargando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cargando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cargando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cargando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Cargando dialog = new Cargando(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBoton_Cancelar;
    // End of variables declaration//GEN-END:variables
}

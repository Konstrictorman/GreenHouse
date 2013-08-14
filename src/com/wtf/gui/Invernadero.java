/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wtf.gui;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.wtf.commons.Configuration;
import com.wtf.commons.Entry;
import com.wtf.commons.RegistrySingleton;
import com.wtf.services.Agent;

/**
 *
 * @author EdwinL
 */
public class Invernadero extends javax.swing.JFrame implements Observer {

	 private Hashtable<String,Entry> hTable;
	    
	private Agent agent;
    /**
     * Creates new form Invernadero
     */
    public Invernadero() throws IOException{
    	initComponents();
        setSize(800,500);
        setLocation(300,300);
        agent = new Agent();
        agent.addObserver(this);
        // add observer to the watched object
		agent.register();
		
		//TODO: La frecuencia la da el dispatcher...
		//agent.setFrecuency(10);
		//agent.measureTemperature();	
    }
    /***
     * Limpiar tabla
     */
    public void limpiarTabla(javax.swing.JTable tabla){
        try {
            DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
            int filas=tabla.getRowCount();
            for (int i = 0;filas>i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }

    
    //LLenar combos
    private void llenarComboDatos(){
       hTable = new Hashtable<String,Entry>() ;
       hTable =RegistrySingleton.getInstance().getAll();
       //Seteamos el titulo del invernadero
       jLabelTitulo.setText(jLabelTitulo.getText()+ " "+Configuration.lOCALHOST);
       comboInvernadero.addItem("all");
       for(String item: hTable.keySet()){
    	   if(item.equals(Configuration.DISPATCHER)) continue;
           comboInvernadero.addItem(item);
       }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        etiquetahost = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultadosTemperatura = new javax.swing.JTable();
        txtFrecuencia = new javax.swing.JTextField();
        btnRegrFrecuencia = new javax.swing.JButton();
        jLabelTitulo = new javax.swing.JLabel();
        comboInvernadero = new javax.swing.JComboBox();
        jBtnLimpiarGrilla = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLblFrecuencia = new javax.swing.JLabel();
        jBtnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(200, 200, 550, 550));
        getContentPane().setLayout(null);

        panelPrincipal.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        etiquetahost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetahost.setText("Invernadero");

        tblResultadosTemperatura.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tblResultadosTemperatura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "host", "Tiempo", "Temperatura"
            }
        ));
        tblResultadosTemperatura.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblResultadosTemperaturaAncestorAdded(evt);
            }
        });
        jScrollPane1.setViewportView(tblResultadosTemperatura);
        tblResultadosTemperatura.getColumnModel().getColumn(0).setHeaderValue("host");
        tblResultadosTemperatura.getColumnModel().getColumn(1).setHeaderValue("Tiempo");
        tblResultadosTemperatura.getColumnModel().getColumn(2).setHeaderValue("Temperatura");

        btnRegrFrecuencia.setText("Registrar Frecuencia");
        btnRegrFrecuencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegrFrecuenciaActionPerformed(evt);
            }
        });

        jLabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("INVERNADERO");

        comboInvernadero.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "seleccione" }));
        comboInvernadero.setKeySelectionManager(null);
        comboInvernadero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboInvernaderoActionPerformed(evt);
            }
        });

        jBtnLimpiarGrilla.setText("Limpiar Grilla");
        jBtnLimpiarGrilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarGrillaActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Frecuencia");

        jLblFrecuencia.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblFrecuencia.setForeground(new java.awt.Color(204, 0, 0));
        jLblFrecuencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jBtnLimpiarGrilla)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                .addComponent(etiquetahost, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboInvernadero, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addComponent(btnRegrFrecuencia)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtFrecuencia, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLblFrecuencia, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(22, 22, 22))))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo)
                .addGap(23, 23, 23)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetahost)
                    .addComponent(comboInvernadero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegrFrecuencia)
                    .addComponent(txtFrecuencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLblFrecuencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnLimpiarGrilla, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(panelPrincipal);
        panelPrincipal.setBounds(30, 20, 710, 350);

        jBtnCerrar.setText("Cerrar");
        jBtnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnCerrar);
        jBtnCerrar.setBounds(600, 370, 140, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegrFrecuenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegrFrecuenciaActionPerformed
       String frecuencia=txtFrecuencia.getText();
       if (frecuencia.equals("")){
           JOptionPane.showMessageDialog(null,"Por favor ingrese una frecuencia a registrar"); 
       }else{
           //Logica registro 
    	   try {
			agent.updateFrecuency(Integer.valueOf(frecuencia));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   
       }       
    }//GEN-LAST:event_btnRegrFrecuenciaActionPerformed

    private void tblResultadosTemperaturaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblResultadosTemperaturaAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tblResultadosTemperaturaAncestorAdded

    private void comboInvernaderoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboInvernaderoActionPerformed
        // TODO add your handling code here:
        String seleccionado =(String)comboInvernadero.getSelectedItem();
        if (seleccionado.equals("seleccione")){
            //limpiarTabla(tblResultadosTemperatura);
            return;
        }
        if (seleccionado.equals("all")){
            DefaultTableModel modelo = (DefaultTableModel) tblResultadosTemperatura.getModel();
            for(String item: hTable.keySet()){
                     comboInvernadero.addItem(item);
                     String[] datos = {item, String.valueOf(2), 2+"F"}; // Cantidad de columnas de la tabla
                     modelo.addRow(datos);
                     for (int i = 2; i < 6; i++) {
                        String[] datos2 = {"", String.valueOf(i), i+"F"}; // Cantidad de columnas de la tabla
                        modelo.addRow(datos2);
                        }
              }
            
        }else{
            Entry item=hTable.get(seleccionado);
            //limpiarTabla(tblResultadosTemperatura);
            
            DefaultTableModel modelo = (DefaultTableModel) tblResultadosTemperatura.getModel();
            for (int i = 1; i < 6; i++) {
                String[] datos = {item.getDestinationId(), String.valueOf(i), i+"F"}; // Cantidad de columnas de la tabla
                modelo.addRow(datos);
            }
            
        }
        
        
    }//GEN-LAST:event_comboInvernaderoActionPerformed

    private void jBtnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCerrarActionPerformed
       try {
		agent.unregister();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    System.exit(0);
       
    }//GEN-LAST:event_jBtnCerrarActionPerformed

    private void jBtnLimpiarGrillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarGrillaActionPerformed
        limpiarTabla(tblResultadosTemperatura);
    }//GEN-LAST:event_jBtnLimpiarGrillaActionPerformed

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
            java.util.logging.Logger.getLogger(Invernadero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Invernadero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Invernadero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Invernadero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
					new Invernadero().setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegrFrecuencia;
    private javax.swing.JComboBox comboInvernadero;
    private javax.swing.JLabel etiquetahost;
    private javax.swing.JButton jBtnCerrar;
    private javax.swing.JButton jBtnLimpiarGrilla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLblFrecuencia;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTable tblResultadosTemperatura;
    private javax.swing.JTextField txtFrecuencia;
    // End of variables declaration//GEN-END:variables
	@Override
	public void update(Observable o, Object arg) {
		if ("REGISTER".equals(arg)) {
			agent.measureTemperature();	
			jLblFrecuencia.setText(String.valueOf(agent.getFrecuency()));
			llenarComboDatos();
		}else if ("REFRESHNODES".equals(arg)){
			llenarComboDatos();
		}else if("CHANGEFRECUENCY".equals(arg)){
			jLblFrecuencia.setText(String.valueOf(agent.getFrecuency()));
		} else if ("TEMPERATURE".equals(arg)) {
			//llenar la tabla con la informacion de temperaturas
		}
		
	}
}

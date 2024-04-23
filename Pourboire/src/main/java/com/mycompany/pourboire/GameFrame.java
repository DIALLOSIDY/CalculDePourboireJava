/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pourboire;
import javax.swing.JFrame ;
import javax.swing.*;
import java.awt.*;

import java.awt.BorderLayout ;
import java.util.HashSet;
import java.util.Set;
import java.text.NumberFormat;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.util.Vector ;


/**
 *
 * @author utilisateur
 */
public class GameFrame extends JFrame {
    private GridBagLayout layout ;
    private GridBagConstraints constraint ;
    private JLabel montantLabel ;
    private JTextField montantText ;
    private JLabel pourcentageLabel ;
    private JSlider pourcentageSlider;
    private JLabel pourboireLabel ;
    private JTextField pourboireText ;
    private JLabel totalLabel ;
    private JTextField totalText ;
   private JLabel calculLabel ;
    private JButton calcul ;
    private JButton totalPourboire ;
    public String ti ;
    public NumberFormat formatPourcentage =NumberFormat.getPercentInstance();
    public NumberFormat formatMonnaie =NumberFormat.getCurrencyInstance();
    
    Vector <Double> tabPourboire =new Vector() ;
    
    
    
    
    public GameFrame(String title){
        super(title);
        layout =new GridBagLayout();
        constraint =new GridBagConstraints();
        constraint.insets =new Insets(10,10,10,10);//le constraint insets de gridbaglayout permet de mettre de lespace autour 
        setLayout(layout);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(14,14,14,14));
        
        montantLabel =new JLabel("montant");
        montantText =new JTextField(12);
        montantText.setToolTipText("Entrer un montant"); //pour afficher un petit message quand on survole
        AddComponent(montantLabel,0,0,1,1);
        AddComponent(montantText,1,0,1,1);
      
       
        
        pourcentageLabel =new JLabel("15");
        pourcentageSlider =new JSlider(JSlider.HORIZONTAL,0,100,15);
        pourcentageSlider.setMajorTickSpacing(25);
        pourcentageSlider.setPaintTicks(true);
       
        pourcentageSlider.addChangeListener((e)->{
           String fr =formatPourcentage.format(pourcentageSlider.getValue()/100.);
           pourcentageLabel.setText(fr);
        });
        
        AddComponent(pourcentageLabel,0,1,1,1);
        AddComponent(pourcentageSlider,1,1,1,1);
        
        
        pourboireLabel=new JLabel("pourboire");
        pourboireText =new JTextField(12);
        pourboireText.setFocusable(false);//pour empecher le focus sur ce 
        pourboireText.setEditable(false);
        AddComponent(pourboireLabel,0,2,1,1);
        AddComponent(pourboireText,1,2,1,1);
        
        
        totalLabel=new JLabel("total");
        totalText =new JTextField(12);
        totalText.setEditable(false); //pour empecher d'ecrire dans le champ de texte 
        totalText.setFocusable(false);
        AddComponent(totalLabel,0,3,1,1);
        AddComponent(totalText,1,3,1,1);
        
        //calculLabel =new JLabel("");
        totalPourboire =new JButton("totalPourboire");
        totalPourboire.setForeground(Color.decode("#12A5A1"));
        calcul=new JButton("calculer");
        constraint.fill=GridBagConstraints.HORIZONTAL; //pour que le boutton occupe tout l'espace 
        AddComponent(totalPourboire,0,4,1,1);
        AddComponent(calcul,1,4,1,1);
        
        totalPourboire.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e){
                Double res =0.;
                for(Double d :tabPourboire){
                    res =res+d ;
                }
                String montantFormate =formatMonnaie.format(res);
                
              JOptionPane.showConfirmDialog(null, "le pourboire total est :"+montantFormate, "pourboire de la soirée", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        
        
        calcul.addActionListener((e)->{
            effectuerCalcul();
           
        });
        
     
        //une fois qu'on appuie sue la touche entrer le calcul est aussi effectuer
        //le boutton entrer agit sur un JTextField si le focus est sur celui si
         montantText.addActionListener((e)->{
            effectuerCalcul();
           
        });
        
        
        ImageIcon icon =new ImageIcon("C:\\Users\\utilisateur\\Documents\\NetBeansProjects\\Pourboire\\src\\main\\java\\Data\\paiement.png");
        this.setIconImage(icon.getImage());
        setSize(400,300);
       
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    public  void AddComponent(Component c,int column ,int line,int width,int height){
        constraint.gridx=column ;
        constraint.gridy=line ;
        constraint.gridwidth= width;
        constraint.gridheight=height ;
        layout.setConstraints(c, constraint);
        add(c);
    }
    
    public void effectuerCalcul(){
        //l'exception de la chaine de caractere est directement levee par le try
        try{
                
                Double montant =Double.parseDouble(montantText.getText());
                if(montant<0){
                    montantText.setText("Entrer un montant valide !");
                    montantText.requestFocus();//pour mettre le focus sur le champ en question 
                    montantText.selectAll();
                    return ;
                }
                Double pourboire =(montant *pourcentageSlider.getValue())/100;
                Double total =montant +pourboire ;
                pourboireText.setText(formatMonnaie.format(pourboire));
                totalText.setText(formatMonnaie.format(total));
                tabPourboire.add(pourboire);
                
            }
            catch(NumberFormatException ev){
                System.out.println("Error :"+ ev.getMessage());
                montantText.setText("Enter un montant valide !");
                montantText.selectAll();
                montantText.requestFocus();
            }
    }
    
}

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.Timer;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Ossa831Proy2 implements ActionListener
{
   JFrame ventana;
   JButton[] btn_botones;
   JButton btn_pres, btn_iniciar, btn_iniciar2, btn_guardar, btn_consultar,btn_rendir;
   JLabel lbl_uni, lbl_fac,lbl_car, lbl_mat, lbl_profe, lbl_nom, lbl_ced, lbl_grup, lbl_fech, lbl_proyec;
   JLabel lbl_cont, lbl_intent, lbl_usuario;
   JTextField tf_cont, tf_intent,tf_usuario;
   Random rdm;
   int presx,presy,espax,espay;
   int lim_inf, lim_sup, lim_izq, lim_der;
   int lim_infc, lim_supc, lim_izqc, lim_derc;
   int diry = 0, dirx=0;
   int diryc = 0, dirxc=0, dirxi2 = 0; 
   int x, y,xc, yc;
   DefaultListModel<String> listModel;
   JList<String> lst_lista;
   JScrollPane scrollPane;
   
   Timer timer = new Timer(1000, new ActionListener()
   {
    public void actionPerformed(ActionEvent e)
    {
        tf_cont.setText(String.valueOf(Integer.parseInt(tf_cont.getText()) + 1));
    }
   });
   Timer timerb = new Timer(5, new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {  
            if(btn_iniciar2==btn_pres)
            {                    
                //System.out.println("Corriendo Timer en Iniciar 2");
                x = btn_botones[14].getLocation().x;
                y = btn_botones[14].getLocation().y;
                btn_botones[14].setLocation(x+dirx,y+diry);
        
                if (x+dirx >= 630)
                    dirx = 0;
    
                xc = btn_botones[15].getLocation().x;
                yc = btn_botones[15].getLocation().y;
                btn_botones[15].setLocation(xc+dirxi2,yc);

                if (xc+dirxi2 <= 570)
                    dirxi2 = 0;
                    
                if(dirx == 0 && dirxi2 == 0)
                {
                    timerb.stop();
                    //System.out.println("Se Detuvo el Timer en iniciar 2");
                }
            }
            for(int i=0;i<16;i++)
            {
                if(btn_pres == btn_botones[i])
                {
            
                    x = btn_pres.getLocation().x;
                    y = btn_pres.getLocation().y;
                    btn_pres.setLocation(x+dirx,y+diry);
                    if (x+dirx >= lim_der)
                        dirx = 0;
                    if (x+dirx <= lim_izq)
                        dirx = 0;
                    if(y+diry <= lim_sup)
                        diry = 0;
                    if(y+diry >= lim_inf)
                        diry = 0;
                    if(dirx == 0 && diry == 0)
                    {
                        timerb.stop();
                        if (verificar())
                        {
                            timer.stop(); 
                            listModel.clear();
                            grabar();
                            JOptionPane.showMessageDialog(ventana, "Felicidades "+tf_usuario.getText()+" terminaste el juego en "+ tf_intent.getText()+" intentos y "+tf_cont.getText()+" segundos.");
                            tf_cont.setText("0");
                            tf_intent.setText("0");
                            tf_usuario.setText("User");
                            btn_guardar.setEnabled(true);
                            tf_usuario.setEnabled(true);
                            btn_rendir.setEnabled(false);               
                            for (int j = 0; j < 16; j++) 
                            {
                                btn_botones[j].setEnabled(false);
                            }
                        } 
                    } 
                }
                if(btn_botones[15] == btn_botones[i])
                {
                    xc = btn_botones[15].getLocation().x;
                    yc = btn_botones[15].getLocation().y;
                    btn_botones[15].setLocation(xc+dirxc,yc+diryc);
                    //System.out.println("Corriendo Timer en Moviento contrario");
                    if (xc+dirxc >= lim_derc)
                        dirxc = 0;
                    if (xc+dirxc <= lim_izqc)
                        dirxc = 0;
                    if(yc+diryc <= lim_supc)
                        diryc = 0;
                    if(yc+diryc >= lim_infc)
                        diryc = 0;

                    if(dirx==0 && diry==0 )
                    {
                        timerb.stop();
                        //System.out.println("Se detuvo en btn_15");
                    }
                } 
            }
         }
      });

  public static void main(String []args)
    {
    	new Ossa831Proy2();
    }
    Ossa831Proy2()
    {
    	ventana = new JFrame("ROMPECABEZAS");
        ventana.setBounds(100,100,900,500);
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        datos(); //Datos del Estudiante con derecho a nota
        btn_botones = new JButton[16];
        for(int i=0;i<16;i++)
        {
        	btn_botones[i] = new JButton(String.valueOf(i+1));
        	btn_botones[i].setBounds(450+60*(i%4),200+60*(i/4),60,60);
            btn_botones[i].addActionListener(this);
            btn_botones[i].setEnabled(false);
        	ventana.add(btn_botones[i]);
        }
        btn_botones[15].setVisible(false);
        btn_iniciar = new JButton("Iniciar");
        btn_iniciar.setBounds(450,130,80,20);
        btn_iniciar.addActionListener(this);
        btn_iniciar.setEnabled(false);
        ventana.add(btn_iniciar);
        btn_iniciar2 = new JButton("Iniciar 2");
        btn_iniciar2.setBounds(610,130,80,20);
        btn_iniciar2.addActionListener(this);
        btn_iniciar2.setEnabled(false);
        ventana.add(btn_iniciar2);
        btn_guardar = new JButton("Guardar");
        btn_guardar.setBounds(525,95,90,20);
        btn_guardar.addActionListener(this);
        ventana.add(btn_guardar);
        btn_consultar = new JButton("Ver Mejores 5 Jugadores");
        btn_consultar.setBounds(850,170,200,20);
        btn_consultar.addActionListener(this);
        ventana.add(btn_consultar);
        btn_rendir = new JButton("Me Rindo");
        btn_rendir.setBounds(540,450,90,20);
        btn_rendir.addActionListener(this);
        btn_rendir.setEnabled(false);
        ventana.add(btn_rendir);
        lbl_cont = new JLabel("Tiempo");
        lbl_cont.setBounds(460,175,60,20);
        ventana.add(lbl_cont);
        tf_cont = new JTextField("0");
        tf_cont.setBounds(515,175,40,20);
        tf_cont.setEditable(false);
        ventana.add(tf_cont); 
        lbl_intent = new JLabel("Intentos");
        lbl_intent.setBounds(580,175,60,20);
        ventana.add(lbl_intent);
        tf_intent = new JTextField("0");
        tf_intent.setBounds(635,175,30,20);
        tf_intent.setEditable(false);
        ventana.add(tf_intent);     
        lbl_usuario = new JLabel("Ingrese su usuario.");
        lbl_usuario.setBounds(515,45,130,20);
        ventana.add(lbl_usuario);
        tf_usuario = new JTextField("User");
        tf_usuario.setBounds(510,65,130,20);
        ventana.add(tf_usuario);
        listModel = new DefaultListModel<String>();
        lst_lista = new JList<String>(listModel);
        scrollPane = new JScrollPane(lst_lista);
        scrollPane.setBounds(800,200,300,200);
        ventana.add(scrollPane);
    
        ventana.setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        //Movienmiento de Botones
        if(!timerb.isRunning())
        {
            timerb.start();
            btn_pres = (JButton)e.getSource(); 
            presx = btn_pres.getLocation().x; 
            presy = btn_pres.getLocation().y;
            espax = btn_botones[15].getLocation().x;
            espay = btn_botones[15].getLocation().y;
            //Limites para el boton precionado
            lim_der = presx+60;
            lim_izq = presx-60;
            lim_inf = presy+60;
            lim_sup = presy-60;
            //Limites para el espacio
            lim_derc = espax+60;
            lim_izqc = espax-60;
            lim_infc = espay+60;
            lim_supc = espay-60;
            //Cambio de Botones
            if((presx-espax)== -60 && (presy==espay) )
            {
                dirx++;
                dirxc--;
            }
            else if((presx-espax)==60 &&(presy==espay))
            {
                dirx--;
                dirxc++;
            }
            else if((presy-espay)==-60&&(presx==espax))
            {
                diry++;
                diryc--;
            }
            else if((presy-espay)==60&&(presx==espax))
            {
                diry--;
                diryc++;
            } 
            for(int i=0;i<16;i++)
            {
                if(btn_pres == btn_botones[i])
                { 
                    tf_intent.setText(String.valueOf(Integer.parseInt(tf_intent.getText())+1));
                } 
            }       
        } 
        if(e.getSource()==btn_guardar)
        {
            tf_usuario.setEnabled(false);
            btn_iniciar.setEnabled(true);
            btn_iniciar2.setEnabled(true);
            btn_guardar.setEnabled(false);
        }
        else if(e.getSource() == btn_iniciar)
        {
            for(int i=0;i<16;i++)
            {
                btn_botones[i].setEnabled(true);
            }
            Revolver1();
            timer.start();
            btn_iniciar.setEnabled(false);
            btn_iniciar2.setEnabled(false);
            btn_rendir.setEnabled(true);
        }
        else if(e.getSource() == btn_iniciar2)
        {
            btn_pres = (JButton)e.getSource();     
            for(int i=0;i<16;i++)
            {
                btn_botones[i].setEnabled(true);
            }
            timer.start();
            timerb.start();
            btn_iniciar.setEnabled(false);
            btn_iniciar2.setEnabled(false);
            btn_rendir.setEnabled(true);
            dirx++;
            dirxi2--;
        }
        else if(e.getSource()==btn_consultar)
        {
            consultar();
        }
        else if(e.getSource()==btn_rendir)
        {
            rendirse();
        }
    }
    public void Revolver1()
    {	
      int i, j, x, y;
      i = 0;
      j = 1;
      Random rnd = new Random();
      for (i=0;i<16;i++)
      {
        j = rnd.nextInt(15);
        x = btn_botones[i].getLocation().x;
        y = btn_botones[i].getLocation().y;
        btn_botones[i].setLocation(btn_botones[j].getLocation().x, btn_botones[j].getLocation().y);
        btn_botones[j].setLocation(x,y);
      }

    }
    private boolean verificar()
    {
        for (int i = 0; i < 15; i++) 
        {
            //System.out.println("En el verificar");
            int correctx = 450+60* (i % 4);
            int correcty = 200+60* (i / 4);
            int actx = btn_botones[i].getLocation().x;
            int acty = btn_botones[i].getLocation().y;
            if (actx != correctx || acty != correcty) 
            {
                return false;
            }
        }
        return true;
    }
    private void grabar()
    {
      try
      { 
        List<String> line_txt = Files.readAllLines(Paths.get("Usuario.txt"));
        if (line_txt.size() >= 6) 
        {
            String ult_l = line_txt.get(5);
            String[] ult_ld = ult_l.split(" ");
            int ult_lc = Integer.parseInt(ult_ld[1]);
            int new_cont = Integer.parseInt(tf_cont.getText());
            if (new_cont < ult_lc) 
            {
                line_txt.set(5, tf_usuario.getText() + " " + tf_cont.getText());
            }
        }
        else 
        {
            line_txt.add(tf_usuario.getText() + " " + tf_cont.getText());
        }
        FileWriter fw = new FileWriter("Usuario.txt", false);  // true=append,  false=overwrite
         for (String line : line_txt) 
        	{
            	fw.write(line + "\n");
        	}
         fw.close();
         top5();
      }
      catch (Exception e)
      {
         System.out.println("Error grabando "+e.toString());
      }
    }
    private void consultar()
    {
        File f = new File("Usuario.txt");
        Scanner sc;
        String usuario;
        try
        {
            sc = new Scanner(f);
            listModel.clear();
            int count = 0;
            while (sc.hasNextLine()&& count < 5)
            {
                usuario = sc.nextLine();
                //System.out.println(usuario);
                listModel.addElement(usuario);
                count++;
            }
            top5();
        }
        catch(Exception e)
        {
            System.out.println("Error leyendo " + e.toString());
        }      
    }
    private void top5() 
	{
        try 
        {
            List<String> doc = Files.readAllLines(Paths.get("Usuario.txt"));
            String[] arr = doc.toArray(new String[0]);
            for (int i = 0; i < arr.length - 1; i++) 
            {
                for (int j = i + 1; j < arr.length; j++) 
                {
                    String[] tempi = arr[i].split(" ");
                    String[] tempj = arr[j].split(" ");
                    int tempiCont = Integer.parseInt(tempi[1]);
                    int tempjcont = Integer.parseInt(tempj[1]);

                    if (tempiCont > tempjcont) 
                    {
                        String temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;
                    }
                }
            }
            FileWriter fw = new FileWriter("Usuario.txt", false);  // Overwrite existing file
            for (String line : arr) 
            {
                fw.write(line + "\n");
            }
            fw.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Error en el ordenado" + e.toString());
        }
    }
    private void rendirse()
    {
        btn_rendir.setEnabled(false);
        timer.stop(); 
        listModel.clear();
        JOptionPane.showMessageDialog(ventana, tf_usuario.getText() + " vuelve a intentarlo.");
        tf_cont.setText("0");
        tf_intent.setText("0");
        tf_usuario.setText("User");
        btn_guardar.setEnabled(true);
        tf_usuario.setEnabled(true); 
        for(int i=0;i<16;i++)
        {
        	btn_botones[i].setBounds(450+60*(i%4),200+60*(i/4),60,60);
            btn_botones[i].setEnabled(false);
        }
    }

    private void datos()
    {
        //Labels de los datos
        lbl_uni = new JLabel("Universidad Tecnológica de Panamá");
        lbl_uni.setBounds(50,90,300,20);
        ventana.add(lbl_uni);
        lbl_fac = new JLabel("Facultad de Ingeniería de Sistemas Computacionales");
        lbl_fac.setBounds(15,115,320,20);
        ventana.add(lbl_fac);
        lbl_car = new JLabel("Licenciatura en Desarrollo de Software");
        lbl_car.setBounds(50,135,300,20);
        ventana.add(lbl_car);
        lbl_proyec = new JLabel("Proyecto No.2");
        lbl_proyec.setBounds(110,200,300,20);
        ventana.add(lbl_proyec);
        lbl_mat = new JLabel("Materia: Desarrollo de Software III");
        lbl_mat.setBounds(65,240,300,20);
        ventana.add(lbl_mat);
        lbl_profe = new JLabel("Profesor: Ricardo Chan");
        lbl_profe.setBounds(85,260,300,20);
        ventana.add(lbl_profe);
        lbl_nom = new JLabel("Estudiante: Humberto Ossa");
        lbl_nom.setBounds(75,300,300,20);
        ventana.add(lbl_nom);
        lbl_ced = new JLabel("Cédula: 8-925-831");
        lbl_ced.setBounds(100,350,300,20);
        ventana.add(lbl_ced);
        lbl_grup = new JLabel("Grupo: 1LS222");
        lbl_grup.setBounds(105,400,300,20);
        ventana.add(lbl_grup);
        lbl_fech = new JLabel("Fecha: 30 de Junio de 2023");
        lbl_fech.setBounds(75,450,300,20);
        ventana.add(lbl_fech);
   }
}

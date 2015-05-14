package Telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Interface.BaseInterface;
import Servidor.EventosDoServidorDeSockets;
import Servidor.ServidorDeSockets;

public class TelaServidor extends BaseInterface implements EventosDoServidorDeSockets {
	
	private JTextField serverPort;
	private JTextField name;
	private JButton connect;
	private JLabel imageUser;
	private JButton image;
	private ServidorDeSockets servidor;
	ImageIcon imgIcon;
	String filename;
	
	
	public TelaServidor() {
		setSize( 300, 480 );
		setTitle( "servidor" );
		setLocationRelativeTo( this );
		setDefaultCloseOperation( TelaCliente.EXIT_ON_CLOSE );
		
		//Here adding separators to JFrame.
		getContentPane().add( getJseparator( 30, 50, 220, 10 ) );
		getContentPane().add( getJseparator( 30, 150, 220, 10 ) );
		
		//Here adding labels to JFrame.
		getContentPane().add( getJLabel( "Servidor:", 30, 30, 100, 23 ) );
		getContentPane().add( getJLabel( "Porta: ", 30, 60, 90, 23 ) );
		getContentPane().add( getJLabel( "Dados pessoais:", 30, 130, 100, 23 ) );
		getContentPane().add( getJLabel( "Nome: ", 30, 160, 90, 23 ) );
		getContentPane().add( getJLabel( "Foto: ", 30, 190, 90, 23 ) );
		getContentPane().add( imageUser = getJLabel( "", 70, 220, 150, 150 ) );
		
		//Here adding text fields to JFrame.
		getContentPane().add( serverPort = getJTextField( 100, 60, 110, 23 ) );
		getContentPane().add( name = getJTextField( 100, 160, 150, 23 ) );
		
		//Here adding buttons to JFrame.
		getContentPane().add( image = getJbutton( "Escolher...", 100, 190, 110, 23 ) );
		getContentPane().add( connect = getJbutton( "Iniciar", 90, 380, 100, 30 ) );
		
		setVisible( true );
		
			image.addActionListener( new ActionListener() {		
				
				@Override
				public void actionPerformed(ActionEvent e) {
					insertImage();
					
				}
			});

			connect.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					startServer();
					
				}
			});
	}
	
	protected void startServer() {
		if( servidor == null ) {
			try {
				servidor = new ServidorDeSockets( Integer.parseInt( serverPort.getText() ), this );
				servidor.start();
				connect.setText( "Finalizar" );
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			servidor.finaliza();
			connect.setText( "Iniciar" );
			servidor = null;
		}
	}
		

	protected void insertImage() {
	    JFileChooser chooser = new JFileChooser();
	    chooser.showOpenDialog(null);
	    File f = chooser.getSelectedFile();
	    filename = f.getAbsolutePath();
	    imgIcon = new ImageIcon( filename );
		
	   imageUser.setIcon( imgIcon );
		
	}

	@Override
	public void aoIniciarServidor() {}

	@Override
	public void aoFinalizarServidor() {}

	@Override
	public void aoReceberSocket(Socket s) {
		Recebedor recebedor = new Recebedor();
		recebedor.start();
		recebedor.setjFrame( true );
		recebedor.setSocket( s );
		recebedor.setServerIcon(imgIcon);
		recebedor.setFileName(filename);

	}

	@Override
	public void reportDeErro(IOException e) {}
	
	public static void main(String[] args) {
		new TelaServidor();

	}
}

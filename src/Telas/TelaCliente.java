package Telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Util.Utilidades;

public class TelaCliente extends Utilidades {
	private JTextField serverAddress;
	private JTextField serverPort;
	private JTextField name;
	private JButton connect;
	private JLabel imageUser;
	private JButton image;
	String filename;
	ImageIcon imgIcon;
	
	
	public TelaCliente() {
		setSize( 300, 500 );
		setLocationRelativeTo( this );
		setTitle( "cliente" );
		setDefaultCloseOperation( TelaCliente.EXIT_ON_CLOSE);

			getContentPane().add( getJseparator( 30, 50, 220, 10 ));		
			getContentPane().add( getJLabel( "Servidor remoto:", 30, 30, 100, 23 ) );
			getContentPane().add( getJLabel( "IP Remoto: ", 30, 60, 90, 23 ) );
			getContentPane().add( getJLabel( "Porta: ", 30, 90, 90, 23 ) );
			getContentPane().add( getJseparator( 30, 180, 220, 10 ) );
			getContentPane().add( getJLabel( "Dados pessoais:", 30, 160, 100, 23 ) );
			getContentPane().add( getJLabel( "Nome: ", 30, 190, 90, 23 ) );
			getContentPane().add( getJLabel( "Foto: ", 30, 220, 90, 23 ) );
			getContentPane().add( imageUser = getJLabel( "", 70, 250, 150, 150 ));
			
			//Here adding text fields to JFrame.
			getContentPane().add( serverAddress = getJTextField( 100, 60, 110, 23 ) );
			getContentPane().add( serverPort = getJTextField( 100, 90, 110, 23 ) );
			getContentPane().add( name = getJTextField( 100, 190, 150, 23 ) );
			
			//Here adding buttons to JFrame.
			getContentPane().add( image = getJbutton( "Escolher...", 100, 220, 110, 23 ) );
			getContentPane().add( connect = getJbutton( "Conectar", 90, 400, 100, 30 ) );
			
			setVisible( true );
			
			//Here adding events on buttons
			image.addActionListener( new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					insertImage();
				}
			});

			connect.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					conectar();
				}
			});
			
	}

	protected void conectar() {

		String address = serverAddress.getText().trim();
		String port = serverPort.getText().trim();
		
		if( address.length() == 0 ) {
			JOptionPane.showMessageDialog( this, "Defina o endereço para conexão" );
			serverAddress.requestFocusInWindow();
			return;
		}
		
		try {
			int portNumber = Integer.parseInt( port );

			try {
				Socket s = new Socket( address, portNumber );
				comunicaComEsteSocket( s );
			} catch( Exception e ) {
				JOptionPane.showMessageDialog( this, "Erro: " + e.getMessage()  );
			}
		} catch( Exception e ) {
			JOptionPane.showMessageDialog( this, "Defina o número da porta para conexão" );
			serverPort.requestFocusInWindow();
			return;
		}
		
	}

	private void comunicaComEsteSocket(Socket s) throws IOException {
		Recebedor recebedor = new Recebedor();
		recebedor.setSocket( s );
		recebedor.setjFrame( true );
		recebedor.start();
		recebedor.setClieIcon( imgIcon );
		
		HashMap<String, Object> transationItens = new HashMap<String, Object>();
		transationItens.put( "nome", name.getText() );
		transationItens.put( "imagem", imageToByte(filename) );
		sendPackage( s, 1, transationItens );
	}

	protected void insertImage() {
	    JFileChooser chooser = new JFileChooser();
	    chooser.showOpenDialog(null);
	    File f = chooser.getSelectedFile();
	    filename = f.getAbsolutePath();
	    imgIcon = new ImageIcon( filename );
	    imageUser.setIcon( imgIcon );
	  	
	}
	
	public static void main(String[] args) {
		new TelaCliente();

	}

}

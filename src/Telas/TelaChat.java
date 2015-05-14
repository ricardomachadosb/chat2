package Telas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONObject;

import Util.Utilidades;


public class TelaChat extends Utilidades implements WindowListener {
	 JLabel label;
	 JTextArea charText;
	 JTextField typedText;
	 JButton sendButton;
	 Socket socket;
	 Recebedor recebedor;
	 JLabel image;
	
	public TelaChat( Socket socket, Recebedor recebedor, ImageIcon serverImage, ImageIcon clientImage ){
		this.socket = socket;
		
		setSize( 710, 460 );
		setLocationRelativeTo( this );
		setLayout( null );
		setResizable( false );
		
		getContentPane().add( image = getJLabel( "", 20,20, 160, 160 ) );
		image.setIcon( clientImage );
		getContentPane().add( image = getJLabel( "", 20,200, 160, 160 ) );
		image.setIcon( serverImage );

		getContentPane().add( charText = getJTextArea( false, 200, 20, 480, 340) );

		getContentPane().add( typedText = getJTextField( 200, 380, 400, 30 ) );
		
		getContentPane().add( sendButton = getJbutton( "Enviar", 600, 380, 80, 30 ) );
		sendButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendTypedText();
				
			}
		});
		
		addWindowListener( this );
		
		setVisible( true );
		
		recebedor.setAreaChat(this.charText);
		recebedor.setBtEnviar(this.sendButton);
		recebedor.setTexto(this.typedText);
		this.recebedor = recebedor;
	}

	protected void sendTypedText() {
		HashMap<String, Object> transationItens = new HashMap<String, Object>();
		transationItens.put( "mensagem", typedText.getText() );
		sendPackage( this.socket, 3, transationItens );
		charText.setText( charText.getText() + "\n Enviando: " + typedText.getText() );
		typedText.setText("");
		
	}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {
		sendPackage( socket, 11 );
	}

	@Override
	public void windowClosing(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}
	
}

package Telas;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import Util.Utilidades;

public class Recebedor extends Thread {
	private Socket socket;
	private boolean jFrame;
	private JTextArea areaChat;
	private JTextField texto;
	private ImageIcon serverIcon;
	private ImageIcon clieIcon;
	private String filename;
	
	Utilidades utilidades = new Utilidades();
	
	public void setFileName(String filename) {
		this.filename = filename;
	}
	
	public void setTexto(JTextField texto) {
		this.texto = texto;
	}

	public void setAreaChat(JTextArea areaChat) {
		this.areaChat = areaChat;
	}

	public void setBtEnviar(JButton btEnviar) {
		this.btEnviar = btEnviar;
	}

	private JButton btEnviar;
	
	public void setjFrame(boolean jFrame) {
		this.jFrame = true;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

		@Override
		public void run() {
			
			try {
				InputStream is = socket.getInputStream();
				DataInputStream dis = new DataInputStream( is );

				while( jFrame ) {
					
					String msg = dis.readUTF();
					if( msg != null ) {
						
						JSONObject rec = new JSONObject( msg );

						switch( rec.getInt( "nroTransacao" ) ) {
							case -1: JOptionPane.showMessageDialog( null, "O usuário recusou a conexão");
								break;
							case 1:	
									setClieIcon(utilidades.byteToImage( (JSONArray) rec.get("imagem")));
									receivedChatSolicitation(rec.getString("nome") );
								break;
							case 2:  
								setServerIcon(utilidades.byteToImage( (JSONArray) rec.get("imagem")));
								new TelaChat( this.socket, this, getClieIcon(), getServerIcon() );
								break;
							case 3: areaChat.setText( areaChat.getText() + "\n Recebido: " + rec.getString( "mensagem" ) );
								break;
							case 11: areaChat.setText( areaChat.getText() + "\n Atenção: o usuário remoto desconectou" );
							texto.setEnabled( false );
							btEnviar.setEnabled( false );
							
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void receivedChatSolicitation( String name ) {
			int resp = JOptionPane.showConfirmDialog( null, "o usuário " + name + " pediu conexao", null, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, getClieIcon() );
			switch (resp) {
			case 0:
				new TelaChat( socket, this, getServerIcon(), getClieIcon() );
				
				HashMap<String, Object> transationItens = new HashMap<String, Object>();

				transationItens.put( "imagem", utilidades.imageToByte(filename));
				utilidades.sendPackage( this.socket, 2, transationItens);
				break;
			case 1:
				utilidades.sendPackage( this.socket, -1);
				break;

			default:
				break;
			}
			
		}

		public ImageIcon getClieIcon() {
			return clieIcon;
		}

		public void setClieIcon(ImageIcon clieIcon) {
			this.clieIcon = clieIcon;
		}

		public ImageIcon getServerIcon() {
			return serverIcon;
		}

		public void setServerIcon(ImageIcon serverIcon) {
			this.serverIcon = serverIcon;
		}
		
	}
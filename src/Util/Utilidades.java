package Util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import Interface.BaseInterface;

public class Utilidades extends BaseInterface{
	
	// convert BufferedImage to byte array
	public byte[] imageToByte( String imagePath ) {	
		byte[] imageInByte;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
			try {
				BufferedImage originalImage = ImageIO.read( new File( imagePath ) );
				ImageIO.write( originalImage, "jpg", baos );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return imageInByte = baos.toByteArray();
	}
	
	// convert byte array back to BufferedImage
	public ImageIcon byteToImage( JSONArray arrayBytes ) {
		
		InputStream in = new ByteArrayInputStream( objectToBytArray(arrayBytes));
		BufferedImage bImageFromConvert = null;
		try {
			bImageFromConvert = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon img = new ImageIcon(bImageFromConvert);
		return img;
	}
	
	/* Convert JSON object to byte.
	 * Used why you cannot cast JSON object to byte[], even if your JSON Object were an byte[]
	 */
	public static byte[] objectToBytArray( JSONArray ob ){
		JSONArray j = (JSONArray) ob;
		byte[] novoB = new byte[j.length()];
		for(int i = 0; i < j.length(); i++){
			try {
				novoB[i] = ((Integer)j.get(i)).byteValue();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return novoB;
	}
	
	public void sendPackage( Socket socket, int transationNumber, HashMap<String, Object> transationItens ) {
		try {
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream( os );

			JSONObject transacao = new JSONObject();
			transacao.put( "nroTransacao", transationNumber );

			for (Entry<String, Object> entry : transationItens.entrySet()) {
				transacao.put( entry.getKey(), entry.getValue() );
			}
			dos.writeUTF( transacao.toString() );
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog( null, "Não foi possível enviar sua mensagem: " + ex.getMessage() );
		}
	}
	
	public void sendPackage( Socket socket, int transationNumber ){
		try {
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream( os );

			JSONObject transacao = new JSONObject();
			transacao.put( "nroTransacao", transationNumber );

			dos.writeUTF( transacao.toString() );
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog( null, "Não foi possível enviar sua mensagem: " + ex.getMessage() );
		}
	}

}

// LoginClient.java 
// LoginClient uses an SSLSocket to transmit fake login 
// information to LoginServer. 


// Java core packages
import java.io.*;

// Java extension packages
import javax.swing.*; 
import javax.net.ssl.*;

public class LoginClient {
	
	// LoginClient constructor
	public LoginClient() {
		// open SSLSocket connection to server and send login
		try {

			// obtain SSLSocketFactory for creating SSLSockets
			SSLSocketFactory socketFactory = 
				( SSLSocketFactory ) SSLSocketFactory.getDefault();
			
			// create SSLSocket from factory
			SSLSocket socket = 
				( SSLSocket ) socketFactory.createSocket(
						"13.49.75.150", 7070 );

			// create PrintWriter for sending login to server
			
			PrintWriter output = new PrintWriter( 
					new OutputStreamWriter( socket.getOutputStream() ) );
			
			//Prompt user for user name
			String userName = JOptionPane.showInputDialog( null, 
					"Enter User Name:" );
			
			// send user name to server
			output.println( userName );
			
			// prompt user for password
			String password = JOptionPane.showInputDialog( null, 
					"Enter Password:" );
						
			// send password to server
			output.println( password );
			output.flush();
			
			
			// create BufferedReader for reading server response

			BufferedReader input = new BufferedReader( 
					new InputStreamReader( socket.getInputStream () ) );
			
			String response = input.readLine();	
			
			// read response from server 
			// display response to user
			JOptionPane.showMessageDialog( null, response );
			
			// clean up streams and SSLSocket
			output.close(); 
			input.close(); 
			socket.close();
			
		} // end try 
		
		
		// handle exception communicating with server
		catch ( IOException ioException ) { 
			ioException.printStackTrace();
		}
		
		// exit application
		finally { 
			System.exit( 0 );
		} 
		
	} // end LoginClient constructor
	
	// execute application
	public static void main( String args[] ) 
	{
		new LoginClient();
		
	}
	
}
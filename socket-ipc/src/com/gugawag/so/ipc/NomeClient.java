package com.gugawag.so.ipc;

/**
 * Client program requesting current date from server.
 *
 * Figure 3.22
 *
 * @author Silberschatz, Gagne and Galvin. Pequenas alterações feitas por Gustavo Wagner (gugawag@gmail.com)
 * Operating System Concepts  - Eighth Edition
 */

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class NomeClient {
	public static void main(String[] args)  {
		try {
			Socket servidorSock = new Socket("127.0.0.1",6013);
			InputStream in = servidorSock.getInputStream();
			BufferedReader bin = new BufferedReader(new InputStreamReader(in));

			System.out.println("=== Cliente iniciado ===\n");

			String line = bin.readLine();
			System.out.println("O servidor me disse:" + line);

			PrintWriter pout = new PrintWriter(servidorSock.getOutputStream(), true);
			pout.println("Renatha do Nascimento Victor");
			servidorSock.close();
		}
		catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}
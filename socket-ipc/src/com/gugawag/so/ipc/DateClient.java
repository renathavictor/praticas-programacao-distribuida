package com.gugawag.so.ipc;

/**
 * Client program requesting current date from server.
 *
 * Figure 3.22
 *
 * @author Silberschatz, Gagne and Galvin. Pequenas alterações feitas por Gustavo Wagner (gugawag@gmail.com)
 * Operating System Concepts  - Eighth Edition
 */

import java.net.*;
import java.io.*;

public class DateClient {
	public static void main(String[] args)  {
		try {
			Socket sock = new Socket("localhost",6013);
			InputStream in = sock.getInputStream();
			BufferedReader bin = new BufferedReader(new InputStreamReader(in));

			System.out.println("=== Cliente iniciado ===\n");
			System.out.println("=== Renatha do Nascimento Victor ===\n");

			String line = bin.readLine();
			System.out.println("O servidor me disse:" + line);

			sock.close();
		}
		catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}

package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Servidor2 {
    private static final String HOME = System.getProperty("user.home");
    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");
        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // laço infinito do servidor
        while (true) {
            List<String> arquivos = new ArrayList<>();

            System.out.println("Cliente: " + socket.getInetAddress());
            System.out.println(HOME);
            String mensagem = dis.readUTF();
            System.out.println(mensagem);

            dos.writeUTF("Opcao escolhida: " + mensagem);
            // TODO
            switch (mensagem) {
                // readdir: devolve o conteúdo de um diretório (lista de nomes)
                case "readdir":
                    Path path = Paths.get(HOME);
                    if (Files.exists(path)) {
                        String resposta = Utils.listarArquivos(HOME);
                        dos.writeUTF(resposta);
                    }
                    break;
                // create: cria um arquivo
                case "create":
                    boolean criouArquivo = Utils.criarArquivo(HOME);
                    if (criouArquivo) {
                        dos.writeUTF("Arquivo criado.");
                        System.out.println("Arquivo criado.");
                    } else {
                        dos.writeUTF("Arquivo já existe.");
                    }
                    break;
                // rename: renomeia um arquivo
                case "rename":
                    File arq1 = new File(HOME + "/testeJava.txt" );
                    File arq2 = new File(HOME + "/testeJava2.txt" );
                    try {
                        Utils.renomearArquivo(arq1, arq2);
                        dos.writeUTF("Arquivo renomeado.");
                    } catch (Exception e) {
                        dos.writeUTF("Erro ao renomear arquivo.");
                    }
                    break;
                // remove: remove um arquivo
                case "remove":
                    try {
                        Utils.deleteArquivo(HOME + "/testeJava2.txt");
                        dos.writeUTF("Arquivo deletado.");
                    } catch (IOException e) {
                        dos.writeUTF("Erro ao deletar arquivo.");
                    }
                    break;
                default:
                    dos.writeUTF("Escolha uma das opçoẽs: readdir, create, rename, remove");
                    break;
            }


        }
        /*
         * Observe o while acima. Perceba que primeiro se lê a mensagem vinda do cliente (linha 29, depois se escreve
         * (linha 32) no canal de saída do socket. Isso ocorre da forma inversa do que ocorre no while do Cliente2,
         * pois, de outra forma, daria deadlock (se ambos quiserem ler da entrada ao mesmo tempo, por exemplo,
         * ninguém evoluiria, já que todos estariam aguardando.
         */
    }
}

package br.edu.ifpb.gugawag.so.sockets;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    public static String listarArquivos(String dir) throws IOException {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.joining(" | "));
    }

    public static boolean criarArquivo(String dir) throws IOException {
        Path novoArquivo = Paths.get(dir + "/testeJava.txt");
        if (Files.exists(novoArquivo)) {
            return false;
        } else {
            Files.createFile(novoArquivo);
            return true;
        }

    }

    public static void renomearArquivo(File arq1, File arq2) throws IOException {
        arq1.renameTo(arq2);
    }

    public static void deleteArquivo(String arq) throws IOException {
        Path arquivoDeletar = Paths.get(arq);
        Files.delete(arquivoDeletar);
    }
}

package com.panbarrosojavier.ejercicios;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.io.IOException;


public class App 
{
    public static int arg0 = 0;

    public static void main( String[] args )
    {
        
        if(args.length > 0){
            try{
                arg0 = Integer.parseInt(args[0]);
                if(arg0 <0){
                    System.err.println("Argumento no válido. Debe ser 0 o mayor.");
                    System.exit(1);
                }
            }
            catch (NumberFormatException e) {
                System.err.println("El primer argumento tiene que ser un número entero.");
                System.exit(1);
            }
        }
        else{
            System.err.println("No se han introducido argumentos.");
            System.exit(1);
        }

        Path directorioPersonal = Paths.get(System.getProperty("user.home"));
        if(arg0 == 0){
            try(Stream<Path> ficheros = Files.list(directorioPersonal)){
            ficheros
            .forEach(f ->{
                String str = mostrarInformacion(f);
                if(!str.equals("")){
                    System.out.println(str);
                }
            });

        }
        catch(IOException e){
            System.out.println("Error al entrar en el directorio");
        }
        }
        else{
            try(Stream<Path> ficheros = Files.list(directorioPersonal)){
            ficheros
            .limit(arg0)
            .forEach(f ->{
                String str = mostrarInformacion(f);
                if(!str.equals("")){
                    System.out.println(str);
                }
            });

        }
        catch(IOException e){
            System.out.println("Error al entrar en el directorio");
        }
        }
        
    }
    public static String mostrarInformacion(Path p){
        String str = "";
        try{
            if(!Files.isHidden(p) || !Files.isSymbolicLink(p)){
                str += p.getFileName();
                if(Files.isDirectory(p)){
                    str +="/";
                }
                if(Files.isRegularFile(p)){
                    str += p.getFileName().toString()+ " | " + 
                    devolverTiempo(p) + " | " +
                    devolverPropietario(p) + " | " +
                    calcularTamano((int)Files.size(p));
                }
            }
            
        }
        catch(IOException e){
            str = "Error obteniendo datos de " + p.getFileName().toString() + e;
        }
        return str;
    }
    public static String devolverPropietario(Path p){
        String resString = "";
        FileOwnerAttributeView arch = Files.getFileAttributeView(p, FileOwnerAttributeView.class);
        try{
            UserPrincipal usu = arch.getOwner();
            resString = usu.toString();
        }
        catch(Exception e){
            resString = "Error";
        }
        return resString;
    }
    public static String devolverTiempo(Path p){
        String resString = "";
        try{
            FileTime tiempo = Files.getLastModifiedTime(p);
            LocalDateTime fecha = LocalDateTime.ofInstant(tiempo.toInstant(), ZoneId.systemDefault());
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            resString = fecha.format(formato);
            
        }
        catch(IOException e){
            resString = "Error";
        }
        return resString;
        

    }
    public static String calcularTamano(int bytes){
        double resul = 0;
        String resString = " bytes";
        if(bytes < 1024 ){
            resul = bytes;
        }
        else if(bytes >=1024 && bytes <1048576){
            resul = bytes / 1024;
            resString = resul + " KB";
        }
        else if(bytes >=1048576 && bytes <1073741824){
            resul = (bytes / 1024) / 1024;
            resString = resul + " MB";
        }
        else{
            resul = bytes / 1073741824;
            resString = resul + "GB";
        }
        return resString;
    }
}

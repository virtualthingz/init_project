package kr.supergate.shoppingfolder.service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import kr.supergate.shoppingfolder.domain.Storage;
import kr.supergate.shoppingfolder.exception.storage.StorageException;
import kr.supergate.shoppingfolder.exception.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService  {

    private final Path rootLocation;

    @Autowired
    public StorageService(Storage properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    final static int SIZE=1024;

    public static void saveFileToUrl(String fAddress, String localFileName, String destinationDir) {
        OutputStream outStream = null;
        URLConnection uCon = null;

        InputStream is = null;
        try {
            URL url = new URL(fAddress);
            byte[] buf;
            int byteRead;
            int byteWritten=0;
            System.out.println(destinationDir+"\\"+localFileName);
            outStream = new BufferedOutputStream(new FileOutputStream(destinationDir +localFileName));
            uCon = url.openConnection();
            is = uCon.getInputStream();
            buf = new byte[SIZE];
            while ((byteRead = is.read(buf)) != -1) {
                outStream.write(buf, 0, byteRead);
                byteWritten += byteRead;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
                outStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fileDownload(String fAddress,String fileName, String destinationDir){
        int slashIndex =fAddress.lastIndexOf('/');
        int periodIndex =fAddress.lastIndexOf('.');
        //String fileName=fAddress.substring(slashIndex + 1);
        if (periodIndex >=1 &&  slashIndex >= 0 && slashIndex < fAddress.length()-1){
        }
        else{
            System.err.println("path or file name.");
        }
    }


    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }


    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }


    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }


    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }


    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}

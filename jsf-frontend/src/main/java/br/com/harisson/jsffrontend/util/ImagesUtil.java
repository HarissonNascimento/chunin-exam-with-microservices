package br.com.harisson.jsffrontend.util;

import br.com.harisson.core.model.Vehicle;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;

@Getter
@Setter
public class ImagesUtil implements Serializable {
    private List<String> listNameFilesToUpload = new ArrayList<>();

    public String getVehicleImagesDirectoryName(Vehicle vehicle, ExternalContext externalContext) {
        return externalContext.getRealPath("resources"
                + File.separator
                + "default"
                + File.separator
                + "images"
                + File.separator
                + vehicle.getImagesFolderDirectory()
        );
    }

    public void deleteVehicleImageFolder(Vehicle vehicle, ExternalContext externalContext){
        File file = new File(getVehicleImagesDirectoryName(vehicle, externalContext));
        boolean dir = file.exists();
        if (dir){
            List<File> fileList = asList(file.listFiles());
            for (File f : fileList){
                f.delete();
            }
            file.delete();
        }
    }

    public void uploadImagesToFolder(UploadedFiles filesToUpload, File file) {
        boolean auxBoolean = file.mkdirs();
        for (UploadedFile uf : filesToUpload.getFiles()) {
            if (!checkIfFileExistsInDirectory(uf.getFileName(), listNameFilesToUpload)) {
                listNameFilesToUpload = returnListUpdatedWithImageNamesOrIOException(file, uf);
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Já existe um arquivo com esse nome.",
                        "");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo enviado com sucesso!", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public boolean checkFolderContainsOneOrMoreFiles() {
        return listNameFilesToUpload.isEmpty();
    }

    public void setThumbnailName(Vehicle vehicle) {
        vehicle.setThumbnailName(listNameFilesToUpload.get(0));
    }

    public List<String> listNamesOfImagesInDirectory(File file) {
        boolean dir = file.mkdir();
        if (!dir) {
            List<String> fileNameList = new ArrayList<>();
            File[] fileList = file.listFiles();
            for (File f : fileList) {
                fileNameList.add(f.getName());
            }
            return fileNameList;
        } else {
            return new ArrayList<>();
        }
    }

    private boolean checkIfFileExistsInDirectory(String fileName, List<String> listNameFilesInDirectory) {
        return listNameFilesInDirectory.contains(fileName);
    }

    private List<String> returnListUpdatedWithImageNamesOrIOException(File file, UploadedFile uf) {
        try (InputStream is = uf.getInputStream()) {
            Files.copy(is,
                    new File(file.getAbsolutePath(),
                            uf.getFileName()).toPath());
            return listNamesOfImagesInDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

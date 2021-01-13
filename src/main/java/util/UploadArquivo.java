/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * <p>UploadArquivo class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
public class UploadArquivo {

    
    public String getRealPath() {
        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();

        return context.getRealPath("/");
    }

    
    public void copyFile(String fileName, InputStream in) {
        try {
            File file = new File(getRealPath() + "/imagens");
            file.mkdirs();
            try (
                OutputStream out = new FileOutputStream(new File(getRealPath() + "/imagens/" + fileName))) {
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                in.close();
                out.flush();
                out.close();
            }
            System.out.println("Arquivo criado.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}

package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SetImg {
    @Autowired
    private Cloudinary cloudinary;

    public String getUrl(MultipartFile file) {
        String url = null;
        try {
            url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("Ci sono stati problemi con l'upload del file!");
        }
        return url;
    }
}

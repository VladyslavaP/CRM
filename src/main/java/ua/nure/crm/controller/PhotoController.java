package ua.nure.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.crm.controller.util.ModelViewConstants;
import ua.nure.crm.controller.util.ResourceNotFoundException;
import ua.nure.crm.controller.util.RoutingConstants;
import ua.nure.crm.service.EmployeeService;
import ua.nure.crm.service.PhotoService;

import javax.ws.rs.BadRequestException;
import java.io.IOException;

import static java.lang.String.format;

@Controller
@RequestMapping(RoutingConstants.PHOTOS)
public class PhotoController {

    private PhotoService photoService;

    private EmployeeService employeeService;

    @Autowired
    public void setPhotoService(PhotoService photoService) {
        this.photoService = photoService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @UpdateCurrentUser
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void uploadPhoto(@RequestParam(ModelViewConstants.USER_ID_PARAMETER) Long userId,
                            @RequestParam(ModelViewConstants.FILE_PARAMETER) MultipartFile file) {
        try {
            String photoId = photoService.storePhoto(file);
            employeeService.updateEmployeePhoto(userId, buildPhotoUrl(photoId));
        } catch (IOException e) {
            throw new BadRequestException(e);
        }
    }

    private String buildPhotoUrl(String photoId) {
        return format("%s/%s", RoutingConstants.PHOTOS, photoId);
    }

    @ResponseBody
    @GetMapping(value = RoutingConstants.PHOTO_ID, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable(ModelViewConstants.PHOTO_ID_PATH_PARAMETER) String photoId) {
        try {
            return photoService.loadPhoto(photoId);
        } catch (IOException e) {
            throw new ResourceNotFoundException();
        }
    }

    @ResponseBody
    @GetMapping(value = RoutingConstants.NO_PHOTO, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getDefaultPhoto(){
        return getPhoto(photoService.getDefaultPhotoId());
    }

}

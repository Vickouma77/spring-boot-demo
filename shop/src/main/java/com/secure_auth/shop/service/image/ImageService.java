package com.secure_auth.shop.service.image;

import com.secure_auth.shop.dto.ImageDto;
import com.secure_auth.shop.exception.ResourceNotFoundException;
import com.secure_auth.shop.model.Image;
import com.secure_auth.shop.model.Product;
import com.secure_auth.shop.repository.ImageRepository;
import com.secure_auth.shop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                () -> { throw new ResourceNotFoundException("Image not found with id: " + id); });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String url = "/api/v1/images/";

                String downloadUrl = url+ image.getId();
                image.setDownloadUrl(downloadUrl);
                Image imageSaved = imageRepository.save(image);

                imageSaved.setDownloadUrl(url+imageSaved.getId());
                imageRepository.save(imageSaved);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(imageSaved.getId());
                imageDto.setImageName(imageSaved.getFileName());
                imageDto.setDownloadUrl(imageSaved.getDownloadUrl());
                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to update image: " + e.getMessage());
        }
    }
}

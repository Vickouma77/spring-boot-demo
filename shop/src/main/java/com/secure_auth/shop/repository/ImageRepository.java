package com.secure_auth.shop.repository;

import com.secure_auth.shop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

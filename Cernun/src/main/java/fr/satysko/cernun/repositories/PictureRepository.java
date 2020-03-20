package fr.satysko.cernun.repositories;

import fr.satysko.cernun.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
    Picture findByIpathAndExtension(String ipath, String extension);
}

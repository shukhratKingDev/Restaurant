package uz.uzkassa.smartposrestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date 07.10.2022 20:00
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {}

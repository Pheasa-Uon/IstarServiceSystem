package com.istar.service.repository.administrator.usersmanagement.permission;

import com.istar.service.entity.administrator.usersmanagement.permission.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    List<Feature> findByParentIsNull(); // For top-level menus
}


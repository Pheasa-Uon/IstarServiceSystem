package com.istar.service.controller.administrator.usersmanagement.permission;

import com.istar.service.entity.administrator.usersmanagement.permission.Feature;
import com.istar.service.service.administrator.usersmanagement.permission.FeatureService;
import com.istar.service.service.administrator.usersmanagement.permission.PermissionService;
import com.istar.service.dto.administrator.usersmanagement.permission.FeaturePermissionDTO;
import com.istar.service.dto.administrator.usersmanagement.permission.FeatureTreeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/features")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @GetMapping
    public ResponseEntity<List<Feature>> getAllFeatures() {
        return ResponseEntity.ok(featureService.getAllFeatures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feature> getFeatureById(@PathVariable Long id) {
        return featureService.getFeatureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Feature> createFeature(@RequestBody Feature feature) {
        return ResponseEntity.ok(featureService.createFeature(feature));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feature> updateFeature(@PathVariable Long id, @RequestBody Feature feature) {
        return ResponseEntity.ok(featureService.updateFeature(id, feature));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeature(@PathVariable Long id) {
        featureService.deleteFeature(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/treetable")
    public ResponseEntity<List<FeatureTreeDTO>> getTreeTable() {
        return ResponseEntity.ok(featureService.getFeatureTree());
    }

}


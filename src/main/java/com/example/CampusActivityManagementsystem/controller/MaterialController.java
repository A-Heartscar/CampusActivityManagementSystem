package com.example.CampusActivityManagementsystem.controller;

import com.example.CampusActivityManagementsystem.entity.Material;
import com.example.CampusActivityManagementsystem.service.MaterialService;
import com.example.CampusActivityManagementsystem.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/materials")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    // 新增物资
    @PostMapping
    public Response<Material> addMaterial(@RequestBody Material material) {
        try {
            return Response.newSuccess(materialService.addMaterial(material));
        } catch (Exception e) {
            return Response.newFail(material, e.getMessage());
        }
    }

    // 根据ID查询物资
    @GetMapping("/{id}")
    public Response<Material> getMaterialById(@PathVariable int id) {
        try {
            return Response.newSuccess(materialService.getMaterialById(id));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 查询所有物资
    @GetMapping
    public Response<List<Material>> getAllMaterials() {
        try {
            return Response.newSuccess(materialService.getAllMaterials());
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 按类型查询物资
    @GetMapping("/type/{mType}")
    public Response<List<Material>> getByType(@PathVariable String mType) {
        try {
            return Response.newSuccess(materialService.getMaterialsByType(mType));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 按名称模糊查询
    @GetMapping("/name/{name}")
    public Response<List<Material>> getByName(@PathVariable String name) {
        try {
            return Response.newSuccess(materialService.getMaterialsByName(name));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 更新物资信息
    @PutMapping("/{id}")
    public Response<Material> updateMaterial(
            @PathVariable int id,
            @RequestBody Material material
    ) {
        try {
            return Response.newSuccess(materialService.updateMaterial(id, material));
        } catch (Exception e) {
            material.setId(id);
            return Response.newFail(material, e.getMessage());
        }
    }

    // 删除物资
    @DeleteMapping("/{id}")
    public Response<Void> deleteMaterial(@PathVariable int id) {
        try {
            materialService.deleteMaterial(id);
            return Response.newSuccess(null);
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }
}

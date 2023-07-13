package com.mikpolv.intensive28.homework.spring.web.controller;

import com.mikpolv.intensive28.homework.spring.persistence.model.Brand;
import com.mikpolv.intensive28.homework.spring.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/brands")
public class BrandController {

  private final BrandService<Brand, Integer> brandService;

  @Autowired
  public BrandController(BrandService<Brand, Integer> brandService) {
    this.brandService = brandService;
  }

  @GetMapping()
  public String getAllBrands(Model model) {
    model.addAttribute("brands", brandService.getBrands());
    return "brand/all_brands";
  }

  @GetMapping("/{id}")
  public String getBrandById(@PathVariable("id") int id, Model model) {
    Optional<Record> optionalRecord = brandService.getBrandById(id);
    if (optionalRecord.isPresent()) {
      model.addAttribute("brand", optionalRecord.get());
      return "brand/brand_by_id";
    } else return "brand/not_exist";
  }

  @GetMapping("/new")
  public String newBrand(Model model) {
    model.addAttribute("brand", new Brand());
    return "brand/create_brand";
  }

  @PostMapping()
  public String createBrand(@RequestParam("name") String name, Model model) {
    Brand brandToCreate = new Brand(name);
    brandService.createBrand(brandToCreate);
    model.addAttribute("brand", brandToCreate);
    return "redirect:/brands";
  }

  @GetMapping("/{id}/edit")
  public String editBrand(Model model, @PathVariable int id) {
    Optional<Record> optionalBrandRecord = brandService.getBrandById(id);
    optionalBrandRecord.ifPresent(record -> model.addAttribute("brand", record));
    return "brand/update_brand";
  }

  @PatchMapping("/{id}")
  public String updateBrand(@ModelAttribute("brand") Brand brand, @PathVariable int id) {
    brandService.updateBrand(id, brand);
    return "redirect:/brands";
  }

  @DeleteMapping("/{id}")
  public String deleteBrand(@PathVariable int id) {
    brandService.deleteBrand(id);
    return "redirect:/brands";
  }
}

package com.mikpolv.intensive28.homework.spring.web.controller;

import com.mikpolv.intensive28.homework.spring.persistence.dto.ResistorRecord;
import com.mikpolv.intensive28.homework.spring.persistence.model.Brand;
import com.mikpolv.intensive28.homework.spring.persistence.model.Resistor;
import com.mikpolv.intensive28.homework.spring.service.BrandService;
import com.mikpolv.intensive28.homework.spring.service.ResistorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/products/resistors")
public class ResistorController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ResistorController.class);
  private final ResistorService<Resistor, Integer> resistorService;
  private final BrandService<Brand, Integer> brandService;

  @Autowired
  public ResistorController(
      ResistorService<Resistor, Integer> resistorService,
      BrandService<Brand, Integer> brandService) {
    this.resistorService = resistorService;
    this.brandService = brandService;
  }

  @GetMapping("/{id}")
  public String getResistorById(@PathVariable("id") int id, Model model) {
    Optional<ResistorRecord> optionalRecord = resistorService.getResistorById(id);
    if (optionalRecord.isPresent()) {
      model.addAttribute("resistor", optionalRecord.get());
      return "product/resistor/resistor_by_id";
    } else return "product/resistor/not_exist";
  }

  @GetMapping("/new")
  public String newResistor(Model model) {
    model.addAttribute("resistor", new Resistor());
    model.addAttribute("brands", brandService.getBrands());
    model.addAttribute("brand", new Brand());
    return "product/resistor/create_resistor";
  }

  @PostMapping()
  public String createResistor(
      @RequestParam("name") String name,
      @RequestParam("partNumber") String partNumber,
      @RequestParam("id") String brandId,
      @RequestParam("resistance") String resistance,
      @RequestParam("voltage") String voltage,
      Model model) {
    Resistor resistorToCreate = new Resistor();
    resistorToCreate.setName(name);
    resistorToCreate.setPartNumber(partNumber);
    resistorToCreate.setVoltage(Integer.parseInt(voltage));
    resistorToCreate.setResistance(Long.parseLong(resistance));
    LOGGER.info("Creating Resistor" + partNumber);
    resistorService.createResistorWithBrandId(resistorToCreate, Integer.parseInt(brandId));
    model.addAttribute("resistor", resistorToCreate);
    return "redirect:/";
  }

  @GetMapping("/{id}/edit")
  public String editResistor(Model model, @PathVariable int id) {
    Optional<ResistorRecord> optionalResistorRecord = resistorService.getResistorById(id);
    if (optionalResistorRecord.isPresent()) {
      Record resistorRecord = optionalResistorRecord.get();
      model.addAttribute("resistor", resistorRecord);
      model.addAttribute("brands", brandService.getBrands());
    }
    optionalResistorRecord.ifPresent(record -> model.addAttribute("resistor", record));
    return "product/resistor/update_resistor";
  }

  @PatchMapping("/{id}")
  public String updateResistor(
      @RequestParam("name") String name,
      @RequestParam("partNumber") String partNumber,
      @RequestParam("brandName") String brandName,
      @RequestParam("resistance") String resistance,
      @RequestParam("voltage") String voltage,
      @PathVariable int id) {
    Resistor resistorToUpdate = new Resistor();
    resistorToUpdate.setName(name);
    resistorToUpdate.setPartNumber(partNumber);
    resistorToUpdate.setResistance(Long.parseLong(resistance));
    resistorToUpdate.setVoltage(Integer.parseInt(voltage));
    resistorService.updateResistorWithBrandName(id, resistorToUpdate, brandName);
    return "redirect:/";
  }

  @DeleteMapping("/{id}")
  public String deleteResistor(@PathVariable int id) {
    resistorService.deleteResistor(id);
    return "redirect:/";
  }
}

package com.mikpolv.intensive28.homework.spring.web.controller;

import com.mikpolv.intensive28.homework.spring.persistence.model.Brand;
import com.mikpolv.intensive28.homework.spring.persistence.model.Wire;
import com.mikpolv.intensive28.homework.spring.service.BrandService;
import com.mikpolv.intensive28.homework.spring.service.WireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/products/wires")
public class WireController {
  private static final Logger LOGGER = LoggerFactory.getLogger(WireController.class);
  private final WireService<Wire, Integer> wireService;
  private final BrandService<Brand, Integer> brandService;

  @Autowired
  public WireController(
      WireService<Wire, Integer> wireService, BrandService<Brand, Integer> brandService) {
    this.wireService = wireService;
    this.brandService = brandService;
  }

  @GetMapping("/{id}")
  public String getWireById(@PathVariable("id") int id, Model model) {
    Optional<Record> optionalRecord = wireService.getWireById(id);
    if (optionalRecord.isPresent()) {
      model.addAttribute("wire", optionalRecord.get());
      return "product/wire/wire_by_id";
    } else return "product/wire/not_exist";
  }

  @GetMapping("/new")
  public String newWire(Model model) {
    model.addAttribute("wire", new Wire());
    model.addAttribute("brands", brandService.getBrands());
    model.addAttribute("brand", new Brand());
    return "product/wire/create_wire";
  }

  @PostMapping()
  public String createWire(
      @RequestParam("name") String name,
      @RequestParam("partNumber") String partNumber,
      @RequestParam("id") String brandId,
      @RequestParam("awg") String awg,
      @RequestParam("outsideDiameter") String outsideDiameter,
      Model model) {
    Wire wireToCreate = new Wire();
    wireToCreate.setName(name);
    wireToCreate.setPartNumber(partNumber);
    wireToCreate.setAwg(awg);
    wireToCreate.setOutsideDiameter(new BigDecimal(outsideDiameter));
    LOGGER.info("Creating Wire" + partNumber);
    wireService.createWireWithBrandId(wireToCreate, Integer.parseInt(brandId));
    model.addAttribute("wire", wireToCreate);
    return "redirect:/";
  }

  @GetMapping("/{id}/edit")
  public String editWire(Model model, @PathVariable int id) {
    Optional<Record> optionalWireRecord = wireService.getWireById(id);
    if (optionalWireRecord.isPresent()) {
      Record wireRecord = optionalWireRecord.get();
      model.addAttribute("wire", wireRecord);
      model.addAttribute("brands", brandService.getBrands());
    }
    optionalWireRecord.ifPresent(record -> model.addAttribute("wire", record));
    return "product/wire/update_wire";
  }

  @PatchMapping("/{id}")
  public String updateWire(
      @RequestParam("name") String name,
      @RequestParam("partNumber") String partNumber,
      @RequestParam("brandName") String brandName,
      @RequestParam("awg") String awg,
      @RequestParam("outsideDiameter") String outsideDiameter,
      @PathVariable int id) {
    Wire wireToUpdate = new Wire();
    wireToUpdate.setName(name);
    wireToUpdate.setPartNumber(partNumber);
    wireToUpdate.setAwg(awg);
    wireToUpdate.setOutsideDiameter(new BigDecimal(outsideDiameter));
    wireService.updateWireWithBrandName(id, wireToUpdate, brandName);
    return "redirect:/";
  }

  @DeleteMapping("/{id}")
  public String deleteWire(@PathVariable int id) {
    wireService.deleteWire(id);
    return "redirect:/";
  }
}

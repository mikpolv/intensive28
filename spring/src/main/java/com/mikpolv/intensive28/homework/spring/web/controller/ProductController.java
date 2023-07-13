package com.mikpolv.intensive28.homework.spring.web.controller;

import com.mikpolv.intensive28.homework.spring.persistence.model.Distributor;
import com.mikpolv.intensive28.homework.spring.persistence.model.Product;
import com.mikpolv.intensive28.homework.spring.service.DistributorService;
import com.mikpolv.intensive28.homework.spring.service.ProductService;
import com.mikpolv.intensive28.homework.spring.service.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
  private final ProductService<Product, Integer> productService;
  private final DistributorService<Distributor, Integer> distributorService;

  @Autowired
  public ProductController(
      ProductService<Product, Integer> productService,
      DistributorService<Distributor, Integer> distributorService) {
    this.productService = productService;
    this.distributorService = distributorService;
  }

  @GetMapping()
  public String getAllProducts(Model model) {
    model.addAttribute("products", productService.getProducts());
    return "product/all_products";
  }

  @GetMapping("/{id}")
  public String getProductById(@PathVariable("id") int id, Model model) {
    Optional<Record> optionalRecord = productService.getProductById(id);

    if (optionalRecord.isPresent()) {
      String productType = productService.getProductType(id);
      LOGGER.info("Product class is {}", productType);
      if (ProductType.WIRE.isEquals(productType)) {
        return "redirect:wires/" + id;
      }
      if (ProductType.RESISTOR.isEquals(productType)) {
        model.addAttribute("resistor", optionalRecord.get());
        return "redirect:resistors/" + id;
      }
      model.addAttribute("product", optionalRecord.get());
      return "product/product_by_id";
    }
    return "product/not_exist";
  }

  @GetMapping("/new")
  public String newProduct() {
    return "product/create";
  }

  @GetMapping("/{id}/set_distributor")
  public String setDistributor(Model model, @PathVariable String id) {
    Optional<Record> optionalRecord = productService.getProductById(Integer.parseInt(id));
    optionalRecord.ifPresent(record -> model.addAttribute("product", record));
    model.addAttribute("distributors", distributorService.getDistributors());
    model.addAttribute("distributor", new Distributor());
    return "product/set_distributor";
  }

  @PatchMapping("/{id}/set_distributor")
  public String setDistributor(@RequestParam("id") String distributorId, @PathVariable int id) {
    productService.setDistributor(id, Integer.parseInt(distributorId));
    return "redirect:/";
  }
}

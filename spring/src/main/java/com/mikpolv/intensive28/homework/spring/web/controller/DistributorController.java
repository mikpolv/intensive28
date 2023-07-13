package com.mikpolv.intensive28.homework.spring.web.controller;

import com.mikpolv.intensive28.homework.spring.persistence.model.Distributor;
import com.mikpolv.intensive28.homework.spring.service.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/distributors")
public class DistributorController {

  private final DistributorService<Distributor, Integer> distributorService;

  @Autowired
  public DistributorController(DistributorService<Distributor, Integer> distributorService) {
    this.distributorService = distributorService;
  }

  @GetMapping()
  public String getAllDistributors(Model model) {
    model.addAttribute("distributors", distributorService.getDistributors());
    return "distributor/all_distributors";
  }

  @GetMapping("/{id}")
  public String getDistributorById(@PathVariable("id") int id, Model model) {
    Optional<Record> optionalRecord = distributorService.getDistributorById(id);
    if (optionalRecord.isPresent()) {
      model.addAttribute("distributor", optionalRecord.get());
      return "distributor/distributor_by_id";
    } else return "distributor/not_exist";
  }

  @GetMapping("/new")
  public String newDistributor(Model model) {
    model.addAttribute("distributor", new Distributor());
    return "distributor/create_distributor";
  }

  @PostMapping()
  public String createDistributor(
      @ModelAttribute("distributor") Distributor distributor) {
    distributorService.createDistributor(distributor);
    return "redirect:/distributors";
  }

  @GetMapping("/{id}/edit")
  public String editDistributor(Model model, @PathVariable int id) {
    Optional<Record> optionalDistributorRecord = distributorService.getDistributorById(id);

    optionalDistributorRecord.ifPresent(record -> model.addAttribute("distributor", record));

    return "distributor/update_distributor";
  }

  @PatchMapping("/{id}")
  public String updateDistributor(
      @ModelAttribute("distributor") Distributor distributor, @PathVariable int id) {
    distributorService.updateDistributor(id, distributor);
    return "redirect:/distributors";
  }

  @DeleteMapping("/{id}")
  public String deleteDistributor(@PathVariable int id) {
    distributorService.deleteDistributor(id);
    return "redirect:/distributors";
  }
}

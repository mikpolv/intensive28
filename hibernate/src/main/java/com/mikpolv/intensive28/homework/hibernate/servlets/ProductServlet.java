package com.mikpolv.intensive28.homework.hibernate.servlets;

import com.mikpolv.intensive28.homework.hibernate.enteties.Brand;
import com.mikpolv.intensive28.homework.hibernate.enteties.Product;
import com.mikpolv.intensive28.homework.hibernate.enteties.Resistor;
import com.mikpolv.intensive28.homework.hibernate.services.BrandService;
import com.mikpolv.intensive28.homework.hibernate.services.ProductService;
import com.mikpolv.intensive28.homework.hibernate.services.ServiceFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
  private static final Logger LOGGER = LoggerFactory.getLogger(SuppliersServlet.class);
  private final ProductService productService = ServiceFactory.getProductService();
  private final BrandService brandService = ServiceFactory.getBrandService();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    doGet(request, response);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String action = request.getParameter("action");
    try (PrintWriter printWriter = response.getWriter()) {
      printWriter.write("You can use parametrise action = ");
      printWriter.println();
      printWriter.write(
          "find(param: id), create_resistor(params: name, brand_id, part_number, resistance, voltage)");
      printWriter.println();
      printWriter.write(
          "remove(param: id), update_resistor(params: id, name, brand_id, part_number, resistance, voltage)");
      printWriter.println();

      if (action.equals("find")) {
        int id = Integer.parseInt(request.getParameter("id"));
        LOGGER.info("Find. Id is provided {}", id);
        Product product = productService.getProductById(id);
        printWriter.write(product.toString());
      }

      if (action.equals("create_resistor")) {
        String productName = request.getParameter("name");
        LOGGER.info("Create. Name is provided {}", productName);
        int brandId = Integer.parseInt(request.getParameter("brand_id"));
        LOGGER.info("Create. BrandId is provided {}", brandId);
        String partNumber = request.getParameter("part_number");
        LOGGER.info("Create. Name is provided {}", productName);
        long resistance = Long.parseLong(request.getParameter("resistance"));
        LOGGER.info("Create. resistance is provided {}", resistance);
        int voltage = Integer.parseInt(request.getParameter("voltage"));
        LOGGER.info("Create. resistance is provided {}", voltage);
        Brand brand = brandService.getBrandById(brandId);
        Resistor resistorToAdd = new Resistor(productName, brand, partNumber, resistance, voltage);

        int newId = productService.createProduct(resistorToAdd);
        if (newId < 0) {
          printWriter.write("Creation failed");

        } else {
          printWriter.write("Created successfully. New product id = " + newId);
        }
      }

      if (action.equals("remove")) {
        int id = Integer.parseInt(request.getParameter("id"));
        LOGGER.info("Remove. Id is provided {}", id);
        if (productService.deleteProductById(id)) {
          printWriter.write("Product id = " + id + " removed!");
        } else {
          printWriter.write("Remove failed");
        }
      }

      if (action.equals("update_resistor")) {
        int id = Integer.parseInt(request.getParameter("id"));
        LOGGER.info("Update. Id is provided {}", id);
        String productName = request.getParameter("name");
        LOGGER.info("Update. Name is provided {}", productName);
        int brandId = Integer.parseInt(request.getParameter("brand_id"));
        LOGGER.info("Update. Brand Id is provided {}", brandId);
        String partNumber = request.getParameter("part_number");
        LOGGER.info("Create. Name is provided {}", productName);
        long resistance = Long.parseLong(request.getParameter("resistance"));
        LOGGER.info("Create. resistance is provided {}", resistance);
        int voltage = Integer.parseInt(request.getParameter("voltage"));
        LOGGER.info("Create. resistance is provided {}", voltage);
        Brand brand = brandService.getBrandById(brandId);
        Resistor resistorToUpdate =
            new Resistor(id, productName, brand, partNumber, resistance, voltage);

        if (productService.updateProduct(resistorToUpdate)) {
          printWriter.write("Product id = " + id + " updated!");
        } else {
          printWriter.write("Update failed");
        }
      }
    }
  }
}

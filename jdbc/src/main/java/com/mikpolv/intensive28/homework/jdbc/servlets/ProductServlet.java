package com.mikpolv.intensive28.homework.jdbc.servlets;

import com.mikpolv.intensive28.homework.jdbc.enteties.Product;
import com.mikpolv.intensive28.homework.jdbc.services.ProductService;
import com.mikpolv.intensive28.homework.jdbc.services.ServiceFactory;
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
      printWriter.write("find(param: id), create(params: name, brand_id)");
      printWriter.println();
      printWriter.write("remove(param: id), update(params: id, name, brand_id)");
      printWriter.println();

      if (action.equals("find")) {
        int id = Integer.parseInt(request.getParameter("id"));
        LOGGER.info("Find. Id is provided {}", id);
        Product product = productService.getProductById(id);
        printWriter.write(product.toString());
      }
      if (action.equals("create")) {
        String productName = request.getParameter("name");
        LOGGER.info("Create. Name is provided {}", productName);
        int brandId = Integer.parseInt(request.getParameter("brand_id"));
        LOGGER.info("Create. BrandId is provided {}", brandId);
        int newId = productService.createProduct(productName, brandId);
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

      if (action.equals("update")) {
        int id = Integer.parseInt(request.getParameter("id"));
        LOGGER.info("Update. Id is provided {}", id);
        String productName = request.getParameter("name");
        LOGGER.info("Update. Name is provided {}", productName);
        int brandId = Integer.parseInt(request.getParameter("brand_id"));
        LOGGER.info("Update. Brand Id is provided {}", brandId);
        if (productService.updateProduct(id, productName, brandId)) {
          printWriter.write("Product id = " + id + " updated!");
        } else {
          printWriter.write("Update failed");
        }
      }
    }
  }
}

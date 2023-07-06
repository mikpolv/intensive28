package com.mikpolv.intensive28.homework.hibernate.servlets;

import com.mikpolv.intensive28.homework.jdbc.enteties.Brand;
import com.mikpolv.intensive28.homework.jdbc.services.BrandService;
import com.mikpolv.intensive28.homework.jdbc.services.ServiceFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/brands")
public class BrandServlet extends HttpServlet {
  private final BrandService brandService = ServiceFactory.getBrandService();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    doGet(request, response);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    PrintWriter printWriter = response.getWriter();
    printWriter.write("Product brands");
    printWriter.println();
    printWriter.println();

    List<Brand> suppliersList = brandService.brandList();
    System.out.printf(suppliersList.toString());
    for (Brand b : suppliersList) {
      printWriter.write(b.toString());
      printWriter.println();
    }
    printWriter.close();
  }
}

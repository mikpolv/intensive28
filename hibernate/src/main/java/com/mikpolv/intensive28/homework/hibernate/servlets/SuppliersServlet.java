package com.mikpolv.intensive28.homework.hibernate.servlets;

import com.mikpolv.intensive28.homework.jdbc.enteties.Supplier;
import com.mikpolv.intensive28.homework.jdbc.services.ServiceFactory;
import com.mikpolv.intensive28.homework.jdbc.services.SupplierService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/suppliers")
public class SuppliersServlet extends HttpServlet {
  private static final Logger LOGGER = LoggerFactory.getLogger(com.mikpolv.intensive28.homework.jdbc.servlets.SuppliersServlet.class);
  private final SupplierService supplierService = ServiceFactory.getSupplierService();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    doGet(request, response);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    try (PrintWriter printWriter = response.getWriter()) {

      printWriter.write("Product suppliers (param: id)");
      printWriter.println();
      printWriter.println();

      int id = Integer.parseInt(request.getParameter("id"));
      LOGGER.info("Id is provided {}", id);
      List<Supplier> suppliersList = supplierService.getProductSuppliers(id);
      LOGGER.info("Received suppliers {}", suppliersList);
      for (Supplier s : suppliersList) {
        printWriter.write(s.toString());
        printWriter.println();
      }
    }
  }
}

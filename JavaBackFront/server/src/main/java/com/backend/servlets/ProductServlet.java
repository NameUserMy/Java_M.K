package com.backend.servlets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload2.core.FileItem;

import com.backend.dal.dao.DataContext;
import com.backend.dal.dto.Product;
import com.backend.rest.RestResponse;
import com.backend.rest.RestService;
import com.backend.services.form_parse.FormParseResult;
import com.backend.services.form_parse.FormParseService;
import com.backend.services.storage.StorageService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Singleton
public class ProductServlet extends HttpServlet {
    private final FormParseService formParseService;
    private final StorageService storageService;
    private final RestService restService;
    private final DataContext dataContext;

    @Inject
    public ProductServlet(DataContext dataContext, RestService restService, StorageService storageService,
            FormParseService formParseService) {
        this.formParseService = formParseService;
        this.storageService = storageService;
        this.restService = restService;
        this.dataContext = dataContext;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FormParseResult formParseResult = formParseService.parseRequest(req);
        RestResponse restResponse = new RestResponse()
                .setResourceUrl("POST /product")
                .setMeta(Map.of(
                        "DataType", "object",
                        "read", "GET /product",
                        "update", "PUT /product",
                        "delete", "DELETE /product"));
        Product product = new Product();
        String str;

        str = formParseResult.getFields().get("product-title");
        if (str == null || str.isBlank()) {

            restService.sendResponse(resp, restResponse.setStatus(400).setData("Missing or empty 'product-title' "));
            return;

        }
        product.setProductTitle(str);

        str = formParseResult.getFields().get("product-description");

        if (str == null || str.isBlank()) {

            restService.sendResponse(resp,
                    restResponse.setStatus(400).setData("Missing or empty 'product-description' "));
            return;

        }
        product.setProductDescription(str);

        str = formParseResult.getFields().get("product-code");

        if (str == null || str.isBlank()) {

            restService.sendResponse(resp,
                    restResponse.setStatus(400).setData("Missing or empty 'product-code' "));
            return;

        }
        product.setProductSlug(str);

        str = formParseResult.getFields().get("product-price");

        try {

            product.setPrice(Double.parseDouble(str));
        } catch (NumberFormatException | NullPointerException ex) {
            restService.sendResponse(resp,
                    restResponse.setStatus(400).setData("Missing or empty 'product-price' "));
            return;

        }

        str = formParseResult.getFields().get("product-stock");

        try {

            product.setStrock(Integer.parseInt(str));
        } catch (NumberFormatException | NullPointerException ex) {
            restService.sendResponse(resp,
                    restResponse.setStatus(400).setData("Missing or empty 'product-stock' "));
            return;

        }

        str = formParseResult.getFields().get("category-id");
        try {

            product.setCategoryId(UUID.fromString(str));
        } catch (Exception ex) {
            restService.sendResponse(resp,
                    restResponse.setStatus(400).setData("Missing or empty 'product-category id' "));
            return;

        }

        FileItem file1 = formParseResult.getFiles().get("product-image");
   
        if (file1.getSize() > 0) {
            int dotPosition = file1.getName().lastIndexOf('.');
            String ext = file1.getName().substring(dotPosition);
            str = storageService.put(file1.getInputStream(), ext);
        }else{

            str=null;
        }

        product.setProductImageId(str);

       // product=dataContext.getProductDao().addNewProduct(product);

        if(product==null){

            //add no commit in bd- delete file
            restService.sendResponse(resp,
                    restResponse.setStatus(500).setData("Internal Error.  See logs"));
            return;


        }

        restService.sendResponse(resp, restResponse.setStatus(200).setData(product));

      
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if ("categories".equals(type)) {// .../product?type=categories

            getCategories(req, resp);

        } else if ("category".equals(type)) { // .../product?type=category&id=1235
            getCategory(req, resp);
        } else {
            getProducts(req, resp);
        }
    }

    private void getCategories(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        restService.sendResponse(resp,
                new RestResponse()
                        .setResourceUrl("GET /product?type=categories")
                        .setMeta(Map.of(
                                "dataType", "array"))
                        .setStatus(200)
                        .setCashTime(86400)
                        .setData(dataContext.getCategoryDao().getList()));

    }

    private void getCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void getProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}

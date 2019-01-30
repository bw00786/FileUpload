package com.atlantic.FileUpload.restcontroller;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.atlantic.FileUpload.model.Customer;
import com.atlantic.FileUpload.model.Product;
import com.atlantic.FileUpload.repositories.CustomerRepository;
import com.atlantic.FileUpload.repositories.ProductRepository;
import com.atlantic.FileUpload.service.UploadForm;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MainRESTController {

    // Linux: /home/{user}/test
    // Windows: C:/Users/{user}/test
    private static String UPLOAD_DIR = System.getProperty("user.home") + "/test";

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/rest/uploadMultiFiles")
    public ResponseEntity<?> uploadFileMulti(@ModelAttribute UploadForm form) throws Exception {

        System.out.println("Description:" + form.getDescription());

        String result = null;
        try {

            result = this.saveUploadedFiles(form.getFiles());

        }
        // Here Catch IOException only.
        // Other Exceptions catch by RestGlobalExceptionHandler class.
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Uploaded to: " + result, HttpStatus.OK);
    }

    // Save Files
    private String saveUploadedFiles(MultipartFile[] files) throws IOException {

        // Make sure directory exists!
        File uploadDir = new File(UPLOAD_DIR);
        uploadDir.mkdirs();

        StringBuilder sb = new StringBuilder();

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue;
            }
            String uploadFilePath = UPLOAD_DIR + "/" + file.getOriginalFilename();

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);

            sb.append(uploadFilePath).append(", ");
        }
        return sb.toString();
    }


    @GetMapping("/rest/getAllFiles")
    public List<String> getListFiles() {
        File uploadDir = new File(UPLOAD_DIR);

        File[] files = uploadDir.listFiles();
        Customer customer = new Customer();
        Product product = new Product();

        List<String> list = new ArrayList<String>();
        for (File file : files) {
            list.add(file.getName());
            try {
                try (
              Reader reader = Files.newBufferedReader(Paths.get(file.getName()));
              CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

          ) {
              for (CSVRecord csvRecord : csvParser) {
                  // Accessing Values by Column Index
                  customer.getCustomerId(Integer.parseInt(csvRecord.get(0)));
                  customer.setFirstName(csvRecord.get(1));
                  customer.setLastName( csvRecord.get(2) );
                  customer.setCity( csvRecord.get( 3 ) );
                  customer.setState( csvRecord.get( 4 ) );
                  customer.setZip( csvRecord.get( 5 ) );


                  // now load product collection
                  product.setProductId( Integer.parseInt(  csvRecord.get(6)) );
                  product.setCustomerId( Integer.parseInt(csvRecord.get(7)));
                  product.setPurchaseOrderStatus( csvRecord.get( 8 ) );
                  product.setProductName( csvRecord.get( 9 ) );
                  product.setOrderAmount( BigDecimal.valueOf( Long.parseLong( csvRecord.get( 10 ) ) ));
                  product.setPruchaseDate( Date.valueOf(csvRecord.get( 11 ) ));
                  int id=Integer.parseInt(csvRecord.get(0));
                  Optional<Customer> optCustomer = customerRepository.findById( String.valueOf( id ) );

                  Customer c = optCustomer.get();
                  if(c.getFirstName() != null)
                   c.setFirstName(c.getFirstName());
                  if(customer.getLastName() != null)
                   c.setLastName(customer.getLastName());
                  if(customer.getCity() != null)
                   c.setCity(customer.getCity());
                  if(customer.getState() != null)
                   c.setState(customer.getState());
                  if(customer.getZip() != null)
                   c.setZip(customer.getZip());
                  customerRepository.save(c);
                // now do product ID
                  Optional<Product> optProduct = productRepository.findById( String.valueOf( id ) );
                  Product p = optProduct.get();
                  if (product.getProductId() != 0)
                      product.setProductId( product.getProductId() );
                  if (p.getCustomerId() !=0)
                      p.setCustomerId( product.getCustomerId() );
                  if (product.getProductName() !=null)
                      p.setProductName( product.getProductName());
                  if (product.getPurchaseOrderStatus() !=null)
                      p.setPurchaseOrderStatus( product.getPurchaseOrderStatus() );
                  if (product.getOrderAmount() !=null)
                      p.setOrderAmount(product.getOrderAmount());
                   productRepository.save( p )  ;
              }
          }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    // @filename: abc.zip,..
    @GetMapping("/rest/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
        File file = new File(UPLOAD_DIR + "/" + filename);
        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }
        Resource resource = new UrlResource(file.toURI());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

}

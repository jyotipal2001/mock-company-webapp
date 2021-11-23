


package com.mockcompany.webapp.service;

/*
 * An import statement allows the current class to use the class being imported
 */
import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
/* The springframework package allows us to take advantage of the spring capabilities */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/* java.util package provides useful utilities */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is the entrypoint for the /api/products/search API.  It is "annotated" with
 * the "RestController" annotation which tells the spring framework that it will be providing
 * API implementations.
 *
 *   https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-web-applications
 *
 * An annotation is metadata that provides data about the program.  Annotations can be checked for on a class by other
 * classes.  In this case, we're telling the spring framework that the SearchController provides API capabilities.
 *
 *   https://docs.oracle.com/javase/tutorial/java/annotations/
 */
@RestController
public class SearchService {

    /**
     * This is a instance field.  It is provided by the spring framework through the constructor because of the
     * @Autowired annotation.  Autowire tells the spring framework to automatically find and use an instance of
     * the declared class when creating this class.
     */
    private final ProductItemRepository productItemRepository;

    @Autowired
    public SearchService(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    /**
     * The search method, annotated with @GetMapping telling spring this method should be called
     * when an HTTP GET on the path /api/products/search is made.  A single query parameter is declared
     * using the @RequestParam annotation.  The value that is passed when performing a query will be
     * in the query parameter.
     * @param query
     * @return The filtered products
     */
    @GetMapping("/api/products/search")
    public Collection<ProductItem> search(@RequestParam("query") String query) {
        

        Iterable<ProductItem> allItems = this.productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();
        boolean exactMatch=false;
        if(query.startsWith("\"")&&query.endsWith("\"")){
            exactMatch=true;
             query=query.substring(1,query.length()-1){
         else{
            query=query.toLowerCase();
        }

        // This is a loop that the code inside will execute on each of the items from the database.
        for (ProductItem item : allItems) {
            boolean nameMatches;
            boolean descMatches;
            if(exactMatch){
                nameMatches=query.equals(item.getName());
                descMatches=query.equals(item.getDescription());
            }
            else{
                nameMatches=item.getName().toLowerCase().contains(query);
                descMatches=item.getDescription().toLowerCase().contains(query);
            }
            if(nameMatches||descMatches)
            // TODO: Figure out if the item should be returned based on the query parameter!
       
            itemList.add(item);
        
        }
        return itemList;
    }
}



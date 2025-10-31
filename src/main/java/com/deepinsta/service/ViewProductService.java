package com.deepinsta.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepinsta.modal.Product;
import com.deepinsta.modal.Users;
import com.deepinsta.modal.ViewProduct;
import com.deepinsta.repository.ProductRepository;
import com.deepinsta.repository.ViewProductRepository;

@Service
public class ViewProductService {
	@Autowired
    private ViewProductRepository viewProductRepository;
	@Autowired
    private ProductService productService;
	@Autowired
    private UsersService userService;
	@Autowired
    private ProductRepository productRepository;
	
	public ViewProduct addView(long id_product, long id_user) {
		Product product=productService.GetProductById(id_product);
		Users user=userService.FindUserByID(id_user);
		ViewProduct view = new ViewProduct();
	        view.setProduct(product);
	        view.setUser(user);
	        view.setViewedAt(LocalDateTime.now());
	        viewProductRepository.save(view);
	        System.out.println("view has registred :"+view);
	        List<ViewProduct> viewList = viewProductRepository.findByIdProduct(product);
	        System.out.println("all View :"+viewList);
	        if (viewList != null) {
	            int totalViews = viewList.size(); // Count the number of view records
	            product.setView(totalViews); // Set view count
	            System.out.println("all views in this product : "+product.getView());
	            System.out.println("all views : "+totalViews);
	            System.out.println("product : "+product);
	            productRepository.save(product);
	        }
	    return view;
	}
	 public void addView(long id_product) {
	        Product product = productService.GetProductById(id_product);
	        List<ViewProduct> viewList = viewProductRepository.findByIdProduct(product);

	        if (viewList != null) {
	            int totalViews = viewList.size(); // Count the number of view records
	            product.setView(totalViews); // Set view count
	            productRepository.save(product);
	        }
	    }
}

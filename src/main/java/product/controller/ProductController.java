package product.controller;

import java.io.FileInputStream;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import product.domain.Product;

@RestController
@RequestMapping(path="/product", produces="application/json")
@CrossOrigin(origins="*")
public class ProductController {
	
	ArrayList<Product> productArray = new ArrayList<>();
	
	@Bean
	public void db() {
		
		FileInputStream serviceAccount;
		{
			try {
				serviceAccount = new FileInputStream("./key.json");

				FirebaseOptions options = FirebaseOptions.builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccount))
						.setDatabaseUrl("https://test-project-8djkd-default-dfadsf.firebaseio.com").build();

				FirebaseApp.initializeApp(options);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference("Product");

		ValueEventListener eventListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				
				for (DataSnapshot ds : dataSnapshot.getChildren()) {				
					Product singleProduct = ds.getValue(Product.class);
					productArray.add(singleProduct);					
				}
				
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
			}
		};
		ref.addListenerForSingleValueEvent(eventListener);
	}
	
	@GetMapping
	public ArrayList<Product> product(@RequestParam("data") String search) {
		
		ArrayList<Product> result = new ArrayList<>();
		
		for(Product product: productArray) {
			if(product.company.contains(search) || product.composition.contains(search) || product.mrp.contains(search) ||
					product.name.contains(search) || product.pack.contains(search) ||
					product.sideEffect.contains(search) || product.substitute.contains(search)) {
				result.add(product);
			}
		}
		
		return result;
	}

}

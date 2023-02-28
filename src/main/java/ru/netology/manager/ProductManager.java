package ru.netology.manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.netology.data.Product;
import ru.netology.repository.ProductRepository;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductManager {
    private ProductRepository repository = new ProductRepository();

    public void add(Product productAdd) {
        repository.addProduct(productAdd);
    }

    public Product[] findAll() {
        return repository.findAll();
    }

    public void removeId(int id) {
        repository.removeId(id);
    }

    public Product[] searchByTitle(String searchInput) {
        Product[] results = new Product[0];
        for (Product product : repository.findAll()) {
            if (matches(product, searchInput)) {
                Product[] tmp = new Product[results.length + 1];
                int i = 0;
                for (Product result : results) {
                    tmp[i] = result;
                    i++;
                }
                tmp[results.length] = product;
                results = tmp;
            }
        }
        return results;
    }

    public boolean matches(Product product, String searchInput) {
        return product.getName().toUpperCase().contains(searchInput.toUpperCase());
    }
}
package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Book;
import ru.netology.data.Product;
import ru.netology.data.Smartphone;
import ru.netology.exception.AlreadyExistsException;
import ru.netology.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {
    ProductRepository repository = new ProductRepository();
    Product book1 = new Book(1, "Гарри Поттер и узник Азкабана", 1500, "Джоан Роулинг");
    Product book2 = new Book(2, "Зелёная миля", 950, "Стивен Кинг");
    Product book3 = new Book(3, "Унесенные ветром", 1350, "Маргарет Митчелл");
    Product smartphone1 = new Smartphone(4, "iPhone 13 pro", 120_000, "Apple");
    Product smartphone2 = new Smartphone(5, "Realme 10", 50_000, "Realme");
    Product smartphone3 = new Smartphone(6, "Xiaomi Redmi Note 11 Pro", 25_000, "Xiaomi");

    @BeforeEach
    void setup() {
        repository.addProduct(book1);
        repository.addProduct(book2);
        repository.addProduct(book3);
        repository.addProduct(smartphone1);
        repository.addProduct(smartphone2);
        repository.addProduct(smartphone3);
    }

    @Test
    void shouldFindByIdSuccess() {
        Product expected = book2;
        int id = 2;
        assertEquals(expected, repository.findById(id));
    }

    @Test
    void shouldFindByIdFailed() {
        int id = 3432;
        assertNull(repository.findById(id));
    }

    //тесты на NotFoundException
    @Test
    void shouldRemoveIdSuccess() {
        Product[] expected = new Product[]{
                book1,
                book2,
                book3,
                smartphone1,
                smartphone3};
        int id = 5;
        repository.removeId(id);
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    void shouldRemoveIdFailed() {
        int id = 17;
        assertThrows(NotFoundException.class, () -> {
            repository.removeId(id);
        });
    }

    @Test
    void shouldAddProductSuccess() {
        Product bookFour = new Book(7, "Свита короля", 850, "Нора Сакавич");
        Product[] expected = new Product[]{
                book1,
                book2,
                book3,
                smartphone1,
                smartphone2,
                smartphone3,
                bookFour};
        repository.addProduct(bookFour);
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    void shouldAddProductFailed() {
        assertThrows(AlreadyExistsException.class, () -> {
            repository.addProduct(book3);
        });
    }
}
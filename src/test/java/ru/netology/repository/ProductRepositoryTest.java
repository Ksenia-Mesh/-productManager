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
    Product book4 = new Book(4, "Исскуство войны", 500, "Сунь-цзы");
    Product book5 = new Book(5, "Прислуга", 500, "Кэтрин Стокетт");
    Product smartphone1 = new Smartphone(6, "iPhone 13 pro", 120_000, "Apple");
    Product smartphone2 = new Smartphone(7, "Realme 10", 50_000, "Realme");
    Product smartphone3 = new Smartphone(8, "Xiaomi Redmi Note 11 Pro", 25_000, "Xiaomi");
    Product smartphone4 = new Smartphone(9, "Galaxy Z Fold3 5G", 130_000, "Samsung");
    Product smartphone5 = new Smartphone(10, "Galaxy A52", 100_000, "Samsung");

    @BeforeEach
    void setup() {
        repository.addProduct(book1);
        repository.addProduct(book2);
        repository.addProduct(book3);
        repository.addProduct(book4);
        repository.addProduct(book5);
        repository.addProduct(smartphone1);
        repository.addProduct(smartphone2);
        repository.addProduct(smartphone3);
        repository.addProduct(smartphone4);
        repository.addProduct(smartphone5);
    }

    @Test
    void shouldFindByIdSuccess() {
        Product expected = book4;
        int id = 4;
        assertEquals(expected, repository.findById(id));
    }

    @Test
    void shouldFindByIdFailed() {
        int id = 3432;
        assertNull(repository.findById(id));
    }

    @Test
    void shouldRemoveIdSuccess() {
        Product[] expected = new Product[]{
                book1,
                book2,
                book3,
                book4,
                book5,
                smartphone1,
                smartphone3,
                smartphone4,
                smartphone5};
        int id = 7;
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
        Product book6 = new Book(11, "Мастер и Маргарита", 1500, "М.А.Булгаков");
        Product[] expected = new Product[]{
                book1,
                book2,
                book3,
                book4,
                book5,
                smartphone1,
                smartphone2,
                smartphone3,
                smartphone4,
                smartphone5,
                book6};
        repository.addProduct(book6);
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    void shouldAddProductFailed() {
        assertThrows(AlreadyExistsException.class, () -> {
            repository.addProduct(book3);
        });
    }
}

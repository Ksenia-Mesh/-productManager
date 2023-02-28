package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.netology.data.Book;
import ru.netology.data.Product;
import ru.netology.data.Smartphone;
import ru.netology.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class ProductManagerTest {
    ProductManager managerEmpty = new ProductManager();
    ProductManager managerOneProduct = new ProductManager();
    ProductManager managerAllProduct = new ProductManager();

    Product book1 = new Book(1, "Гарри Поттер и узник Азкабана", 1500, "Джоан Роулинг");
    Product book2 = new Book(2, "Зелёная миля", 950, "Стивен Кинг");
    Product book3 = new Book(3, "Унесенные ветром", 1350, "Маргарет Митчелл");
    Product smartphone1 = new Smartphone(4, "iPhone 13 pro", 120_000, "Apple");
    Product smartphone2 = new Smartphone(5, "Realme 10", 50_000, "Realme");
    Product smartphone3 = new Smartphone(6, "Xiaomi Redmi Note 11 Pro", 25_000, "Xiaomi");

    @BeforeEach
    void setup() {
        managerOneProduct.add(book1);
        managerAllProduct.add(book1);
        managerAllProduct.add(book2);
        managerAllProduct.add(book3);
        managerAllProduct.add(smartphone1);
        managerAllProduct.add(smartphone2);
        managerAllProduct.add(smartphone3);
    }

    @Test
    void shouldAddRepositoryEmpty() {
        Product[] expected = new Product[]{smartphone1};
        managerEmpty.add(smartphone1);
        assertArrayEquals(expected, managerEmpty.findAll());
    }

    @Test
    void shouldAddRepositoryWithOneProduct() {
        Product[] expected = new Product[]{
                book1,
                smartphone1};
        managerOneProduct.add(smartphone1);
        assertArrayEquals(expected, managerOneProduct.findAll());
    }

    @Test
    void shouldAddRepositoryWithTenProduct() {
        Product bookFour = new Book(7, "Свита короля", 850, "Нора Сакавич");
        Product[] expected = new Product[]{
                book1,
                book2,
                book3,
                smartphone1,
                smartphone2,
                smartphone3,
                bookFour};
        managerAllProduct.add(bookFour);
        assertArrayEquals(expected, managerAllProduct.findAll());
    }

    @Test
    void shouldRemoveIdFromRepositoryWithOneProduct() {
        int id = 1;
        Product[] expected = new Product[0];
        managerOneProduct.removeId(id);
        assertArrayEquals(expected, managerOneProduct.findAll());
    }

    @Test
    void shouldRemoveIdFromRepositoryWithTenProduct() {
        int idBook = 3;
        int idSmartphone = 5;
        Product[] expected = new Product[]{
                book1,
                book2,
                smartphone1,
                smartphone3,};
        managerAllProduct.removeId(idBook);
        managerAllProduct.removeId(idSmartphone);
        assertArrayEquals(expected, managerAllProduct.findAll());
    }

    @Test
    void shouldMatchesTrue() {
        assertTrue(managerEmpty.matches(smartphone1, "iPhone 13 pro"));
    }

    @Test
    void shouldMatchesFalse() {
        assertFalse(managerEmpty.matches(smartphone1, "Apple"));
    }

    @Mock
    ProductRepository repository = Mockito.mock(ProductRepository.class);
    @InjectMocks
    ProductManager managerWithMock = new ProductManager(repository);

    @Test
    void shouldSearchByNameEmptyMock() {
        Product[] returnedEmpty = new Product[0];
        doReturn(returnedEmpty).when(repository).findAll();
        Product[] expected = new Product[0];
        assertArrayEquals(expected, managerWithMock.searchByTitle(" "));
    }



//    @Test
//    void shouldSearchByTitleMockWithAllProductFoundOneResult() {
//        Product[] returnedAllProduct = new Product[]{
//                book1,
//                book2,
//                book3,
//                smartphone1,
//                smartphone2,
//                smartphone3};
//        doReturn(returnedAllProduct).when(repository).findAll();
//        Product[] expected = new Product[]{book3};
//        assertArrayEquals(expected, managerWithMock.searchByTitle("ветром"));
//    }

//    @Test
//    void shouldSearchByTitleMockWithAllProductFoundMoreResult() {
//        Product[] returnedAllProduct = new Product[]{
//                book1,
//                book2,
//                book3,
//                smartphone1,
//                smartphone2,
//                smartphone3};
//        doReturn(returnedAllProduct).when(repository).findAll();
//        Product[] expected = new Product[]{
//                book1,
//                smartphone1,
//                smartphone2};
//        assertArrayEquals(expected, managerWithMock.searchByTitle("1"));
//    }

    @Test
    void shouldSearchByTitleMockWithAllProductUnFound() {
        Product[] returnedAllProduct = new Product[]{
                book1,
                book2,
                book3,
                smartphone1,
                smartphone2,
                smartphone3};
        doReturn(returnedAllProduct).when(repository).findAll();
        Product[] expected = new Product[0];
        assertArrayEquals(expected, managerWithMock.searchByTitle("толстой"));
    }
}

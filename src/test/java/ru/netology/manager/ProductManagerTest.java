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
    Product book4 = new Book(4, "Исскуство войны", 500, "Сунь-цзы");
    Product book5 = new Book(5, "Прислуга", 500, "Кэтрин Стокетт");
    Product smartphone1 = new Smartphone(6, "iPhone 13 pro", 120_000, "Apple");
    Product smartphone2 = new Smartphone(7, "Realme 10", 50_000, "Realme");
    Product smartphone3 = new Smartphone(8, "Xiaomi Redmi Note 11 Pro", 25_000, "Xiaomi");
    Product smartphone4 = new Smartphone(9, "Galaxy Z Fold3 5G", 130_000, "Samsung");
    Product smartphone5 = new Smartphone(10, "Galaxy A52", 100_000, "Samsung");

    @BeforeEach
    void setup() {
        managerOneProduct.add(book1);
        managerAllProduct.add(book1);
        managerAllProduct.add(book2);
        managerAllProduct.add(book3);
        managerAllProduct.add(book4);
        managerAllProduct.add(book5);
        managerAllProduct.add(smartphone1);
        managerAllProduct.add(smartphone2);
        managerAllProduct.add(smartphone3);
        managerAllProduct.add(smartphone4);
        managerAllProduct.add(smartphone5);
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
        managerAllProduct.add(book6);
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
        int idSmartphone = 7;
        Product[] expected = new Product[]{
                book1,
                book2,
                book4,
                book5,
                smartphone1,
                smartphone3,
                smartphone4,
                smartphone5};
        managerAllProduct.removeId(idBook);
        managerAllProduct.removeId(idSmartphone);
        assertArrayEquals(expected, managerAllProduct.findAll());
    }
    
    @Test
    void shouldMatchesTrue() {
        assertTrue(managerEmpty.matches(smartphone1, "iphONE 13"));
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
    void shouldSearchByTitleEmptyMock() {
        Product[] returnedEmpty = new Product[0];
        doReturn(returnedEmpty).when(repository).findAll();
        Product[] expected = new Product[0];
        assertArrayEquals(expected, managerWithMock.searchByTitle(" "));
    }

    @Test
    void shouldSearchByTitleMockWithOneProductFound() {
        Product[] returnedOneProduct = new Product[]{smartphone4};
        doReturn(returnedOneProduct).when(repository).findAll();
        Product[] expected = new Product[]{smartphone4};
        assertArrayEquals(expected, managerWithMock.searchByTitle("fold3"));
    }

    @Test
    void shouldSearchByTitleMockWithOneProductUnFound() {
        Product[] returnedOneProduct = new Product[]{smartphone4};
        doReturn(returnedOneProduct).when(repository).findAll();
        Product[] expected = new Product[0];
        assertArrayEquals(expected, managerWithMock.searchByTitle("iPhone"));
    }

    @Test
    void shouldSearchByTitleMockWithAllProductFoundOneResult() {
        Product[] returnedAllProduct = new Product[]{
                book1,
                book2,
                book3,
                book4,
                book5,
                smartphone1,
                smartphone2,
                smartphone3,
                smartphone4,
                smartphone5};
        doReturn(returnedAllProduct).when(repository).findAll();
        Product[] expected = new Product[]{bookFour};
        assertArrayEquals(expected, managerWithMock.searchByTitle("исскуство"));
    }

    @Test
    void shouldSearchByTitleMockWithAllProductFoundMoreResult() {
        Product[] returnedAllProduct = new Product[]{
                book1,
                book2,
                book3,
                book4,
                book5,
                smartphone1,
                smartphone2,
                smartphone3,
                smartphone4,
                smartphone5,};
        doReturn(returnedAllProduct).when(repository).findAll();
        Product[] expected = new Product[]{
                book1,
                smartphone1,
                smartphone2,
                smartphone4};
        assertArrayEquals(expected, managerWithMock.searchByTitle("3"));
    }

    @Test
    void shouldSearchByTitleMockWithAllProductUnFound() {
        Product[] returnedAllProduct = new Product[]{
                book1,
                book2,
                book3,
                book4,
                book5,
                smartphone1,
                smartphone2,
                smartphone3,
                smartphone4,
                smartphone5,};
        doReturn(returnedAllProduct).when(repository).findAll();
        Product[] expected = new Product[0];
        assertArrayEquals(expected, managerWithMock.searchByTitle("толстой"));
    }
}

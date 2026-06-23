package cl.duoc.venta.service;

// ===== IMPORTS DEL PROYECTO =====

import cl.duoc.venta.client.ClienteClient;
import cl.duoc.venta.client.EntradaClient;
import cl.duoc.venta.dto.VentaResponseDTO;
import cl.duoc.venta.model.ModeloVenta;
import cl.duoc.venta.repository.VentaRepository;

// ===== IMPORTS DE JUNIT =====

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// ===== IMPORTS DE MOCKITO =====

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

// ===== IMPORTS JAVA =====

import java.util.List;
import java.util.Optional;

// ===== IMPORTS ASSERTIONS =====

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// ===== NUEVO =====
// DataFaker genera datos falsos para pruebas

import net.datafaker.Faker;

public class VentaServiceTest {

    // ==================================================
    // MOCKS
    // ==================================================

    // Simula el repositorio
    // NO usa MySQL real

    @Mock
    private VentaRepository repository;

    // Simula el microservicio Cliente

    @Mock
    private ClienteClient clienteClient;

    // Simula el microservicio Entrada

    @Mock
    private EntradaClient entradaClient;

    // ==================================================
    // SERVICIO A PROBAR
    // ==================================================

    // Crea un VentaService e inyecta automáticamente
    // todos los mocks anteriores

    @InjectMocks
    private VentaService service;

    // ==================================================
    // CONFIGURACIÓN INICIAL
    // ==================================================

    @BeforeEach
    void setUp() {

        // Inicializa Mockito antes de cada prueba

        MockitoAnnotations.openMocks(this);
    }

    // ==================================================
    // TEST 1
    // LISTAR VENTAS
    // ==================================================

    @Test
    @DisplayName("Debe listar ventas")
    void testListar() {

        // Venta falsa para la prueba

        ModeloVenta venta =
                new ModeloVenta(
                        1L,
                        1L,
                        1L,
                        7000
                );

        // Cuando se ejecute findAll()
        // devolverá una lista con nuestra venta falsa

        when(repository.findAll())
                .thenReturn(List.of(venta));

        // Ejecuta el método real

        List<VentaResponseDTO> resultado =
                service.listar();

        // Verificaciones

        assertNotNull(resultado);

        assertEquals(
                1,
                resultado.size()
        );

        assertEquals(
                7000,
                resultado.get(0).getTotal()
        );
    }

    // ==================================================
    // TEST 2
    // BUSCAR POR ID
    // ==================================================

    @Test
    @DisplayName("Debe buscar una venta por ID")
    void testBuscarPorId() {

        ModeloVenta venta =
                new ModeloVenta(
                        1L,
                        1L,
                        1L,
                        7000
                );

        // Simula que existe una venta con ID 1

        when(repository.findById(1L))
                .thenReturn(Optional.of(venta));

        VentaResponseDTO resultado =
                service.buscarPorId(1L);

        assertNotNull(resultado);

        assertEquals(
                1L,
                resultado.getId()
        );

        assertEquals(
                7000,
                resultado.getTotal()
        );
    }

    // ==================================================
    // TEST 3
    // ELIMINAR
    // ==================================================

    @Test
    @DisplayName("Debe eliminar una venta")
    void testEliminar() {

        // Simula que la venta existe

        when(repository.existsById(1L))
                .thenReturn(true);

        service.eliminar(1L);

        // Verifica que realmente se llamó deleteById

        verify(repository)
                .deleteById(1L);
    }

    // ==================================================
    // TEST 4
    // EXCEPCIÓN
    // ==================================================

    @Test
    @DisplayName("Debe lanzar excepción cuando la venta no existe")
    void testVentaNoExiste() {

        // Simula que NO existe la venta

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException ex =
                assertThrows(
                        RuntimeException.class,
                        () -> service.buscarPorId(99L)
                );

        assertEquals(
                "La venta no existe",
                ex.getMessage()
        );
    }

    // ==================================================
    // TEST 5 (NUEVO)
    // DATAFAKER
    // ==================================================

    @Test
    @DisplayName("Debe generar datos falsos con DataFaker")
    void testDataFaker() {

        // Crea el generador de datos falsos

        Faker faker = new Faker();

        // Genera un nombre aleatorio

        String nombreCliente =
                faker.name().fullName();

        // Genera un total aleatorio

        int total =
                faker.number()
                        .numberBetween(
                                5000,
                                15000
                        );

        // Verifica que el nombre no sea nulo

        assertNotNull(nombreCliente);

        // Verifica que el número esté dentro del rango

        assertTrue(total >= 5000);

        assertTrue(total <= 15000);

        // Solo para visualizar en consola

        System.out.println(
                "Cliente Faker: "
                        + nombreCliente
        );

        System.out.println(
                "Total Faker: "
                        + total
        );
    }
}
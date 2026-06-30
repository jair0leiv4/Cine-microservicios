package cl.duoc.venta.controller;

import cl.duoc.venta.dto.VentaRequestDTO;
import cl.duoc.venta.dto.VentaResponseDTO;
import cl.duoc.venta.service.VentaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VentaControllerTest {

    @Mock
    private VentaService service;

    @InjectMocks
    private VentaController controller;

    private VentaResponseDTO ventaResponse;
    private VentaRequestDTO ventaRequest;

    @BeforeEach
    void setUp() {
        // GIVEN: datos base reutilizados en varios tests
        ventaResponse = new VentaResponseDTO(1L, 10L, 20L, 5000);
        ventaRequest = new VentaRequestDTO(10L, 20L, 5000);
    }

    @Test
    void listar_deberiaRetornarListaDeVentasConStatus200() {
        // GIVEN
        when(service.listar()).thenReturn(List.of(ventaResponse));

        // WHEN
        ResponseEntity<List<VentaResponseDTO>> respuesta = controller.listar();

        // THEN
        assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respuesta.getBody()).hasSize(1);
        assertThat(respuesta.getBody().get(0).getTotal()).isEqualTo(5000);
        verify(service, times(1)).listar();
    }

    @Test
    void buscarPorId_deberiaRetornarVentaConEnlacesHateoasYStatus200() {
        // GIVEN
        when(service.buscarPorId(1L)).thenReturn(ventaResponse);

        // WHEN
        ResponseEntity<VentaResponseDTO> respuesta = controller.buscarPorId(1L);

        // THEN
        assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respuesta.getBody().getId()).isEqualTo(1L);
        assertThat(respuesta.getBody().getLinks()).isNotEmpty();
        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
    void guardar_deberiaCrearVentaYRetornarStatus201() {
        // GIVEN
        when(service.guardar(any(VentaRequestDTO.class))).thenReturn(ventaResponse);

        // WHEN
        ResponseEntity<VentaResponseDTO> respuesta = controller.guardar(ventaRequest);

        // THEN
        assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(respuesta.getBody().getClienteId()).isEqualTo(10L);
        verify(service, times(1)).guardar(ventaRequest);
    }

    @Test
    void actualizar_deberiaActualizarVentaYRetornarStatus200() {
        // GIVEN
        VentaResponseDTO actualizada = new VentaResponseDTO(1L, 10L, 20L, 7000);
        when(service.actualizar(anyLong(), any(VentaRequestDTO.class))).thenReturn(actualizada);

        // WHEN
        ResponseEntity<VentaResponseDTO> respuesta = controller.actualizar(1L, ventaRequest);

        // THEN
        assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respuesta.getBody().getTotal()).isEqualTo(7000);
        verify(service, times(1)).actualizar(1L, ventaRequest);
    }

    @Test
    void eliminar_deberiaEliminarVentaYRetornarMensajeConStatus200() {
        // WHEN
        ResponseEntity<String> respuesta = controller.eliminar(1L);

        // THEN
        assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respuesta.getBody()).isEqualTo("Venta eliminada correctamente");
        verify(service, times(1)).eliminar(1L);
    }
}
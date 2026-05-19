package cl.duoc.entrada.model; // paquete del modelo

import jakarta.persistence.Entity; // librerias JPA
import jakarta.persistence.GeneratedValue; // constructor con parametros
import jakarta.persistence.GenerationType; // getters setters toString
import jakarta.persistence.Id; // constructor vacio
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // indica que es una entidad de BD
@Table(name = "entradas") // nombre de tabla
@Data // genera getters y setters automaticamente
@NoArgsConstructor // constructor vacio
@AllArgsConstructor // constructor con todos los atributos
public class ModeloEntrada {

    @Id // llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto incremento
    private Long id;

    private String tipo; // tipo de entrada

    private Integer precio; // precio entrada

    private Long funcionId; // id de funcion relacionada
}
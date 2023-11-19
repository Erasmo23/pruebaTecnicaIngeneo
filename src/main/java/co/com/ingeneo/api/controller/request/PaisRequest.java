package co.com.ingeneo.api.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PaisRequest {

    @NotBlank(message = "No puede estar vacio el campo nombre")
    @Size(max = 100, message = "El campo nombre excede la longitud permitida")
    private String nombre; 

    @NotBlank(message = "No puede estar vacio el campo codigoIso")
    @Size(max = 3, message = "El campo codigoIso excede la longitud permitida")
    private String codigoIso; 

}
package io.github.jhipster.application.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.TareasEstado;

/**
 * A DTO for the {@link io.github.jhipster.application.domain.Tarea} entity.
 */
public class TareaDTO implements Serializable {

    private Long id;

    private String descripcion;

    private TareasEstado estado;

    private Instant fechaCreado;

    private Instant fechaPrevistoInicio;

    private Instant fechaInicio;

    private Instant fechaFinal;

    private Integer horasPrevisto;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TareasEstado getEstado() {
        return estado;
    }

    public void setEstado(TareasEstado estado) {
        this.estado = estado;
    }

    public Instant getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(Instant fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public Instant getFechaPrevistoInicio() {
        return fechaPrevistoInicio;
    }

    public void setFechaPrevistoInicio(Instant fechaPrevistoInicio) {
        this.fechaPrevistoInicio = fechaPrevistoInicio;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Instant fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Integer getHorasPrevisto() {
        return horasPrevisto;
    }

    public void setHorasPrevisto(Integer horasPrevisto) {
        this.horasPrevisto = horasPrevisto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TareaDTO tareaDTO = (TareaDTO) o;
        if (tareaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tareaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TareaDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", fechaCreado='" + getFechaCreado() + "'" +
            ", fechaPrevistoInicio='" + getFechaPrevistoInicio() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFinal='" + getFechaFinal() + "'" +
            ", horasPrevisto=" + getHorasPrevisto() +
            "}";
    }
}

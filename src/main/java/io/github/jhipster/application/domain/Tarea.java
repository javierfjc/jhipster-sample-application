package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.TareasEstado;

/**
 * A Tarea.
 */
@Entity
@Table(name = "tarea")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tarea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private TareasEstado estado;

    @Column(name = "fecha_creado")
    private Instant fechaCreado;

    @Column(name = "fecha_previsto_inicio")
    private Instant fechaPrevistoInicio;

    @Column(name = "fecha_inicio")
    private Instant fechaInicio;

    @Column(name = "fecha_final")
    private Instant fechaFinal;

    @Column(name = "horas_previsto")
    private Integer horasPrevisto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Tarea descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TareasEstado getEstado() {
        return estado;
    }

    public Tarea estado(TareasEstado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(TareasEstado estado) {
        this.estado = estado;
    }

    public Instant getFechaCreado() {
        return fechaCreado;
    }

    public Tarea fechaCreado(Instant fechaCreado) {
        this.fechaCreado = fechaCreado;
        return this;
    }

    public void setFechaCreado(Instant fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public Instant getFechaPrevistoInicio() {
        return fechaPrevistoInicio;
    }

    public Tarea fechaPrevistoInicio(Instant fechaPrevistoInicio) {
        this.fechaPrevistoInicio = fechaPrevistoInicio;
        return this;
    }

    public void setFechaPrevistoInicio(Instant fechaPrevistoInicio) {
        this.fechaPrevistoInicio = fechaPrevistoInicio;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public Tarea fechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFinal() {
        return fechaFinal;
    }

    public Tarea fechaFinal(Instant fechaFinal) {
        this.fechaFinal = fechaFinal;
        return this;
    }

    public void setFechaFinal(Instant fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Integer getHorasPrevisto() {
        return horasPrevisto;
    }

    public Tarea horasPrevisto(Integer horasPrevisto) {
        this.horasPrevisto = horasPrevisto;
        return this;
    }

    public void setHorasPrevisto(Integer horasPrevisto) {
        this.horasPrevisto = horasPrevisto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tarea)) {
            return false;
        }
        return id != null && id.equals(((Tarea) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tarea{" +
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

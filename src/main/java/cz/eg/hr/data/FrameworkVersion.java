package cz.eg.hr.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Entity
@Validated
public class FrameworkVersion {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn
    @JsonIgnore
    private JavascriptFramework framework;

    @Range(min = 1, max = 100)
    @NotNull
    @Column
    private Integer version;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endOfSupport;

    public Long getId() {
        return id;
    }

    public JavascriptFramework getFramework() {
        return framework;
    }
    public void setFramework(JavascriptFramework framework) {
        this.framework = framework;
    }

    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }

    public LocalDate getEndOfSupport() {
        return endOfSupport;
    }
    public void setEndOfSupport(LocalDate endOfSupport) {
        this.endOfSupport = endOfSupport;
    }
}

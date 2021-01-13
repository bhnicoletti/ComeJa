
package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
@Entity
public class Endereco implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEndereco;
    private String estadoEndereco;
    private String cidadeEndereco;
    private String bairroEndereco;
    private String ruaEndereco;
    private String numeroEndereco;
    private String complementoEndereco;
    private String cepEndereco;
    private String status;  
    private Long pessoa;

    public Long getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getEstadoEndereco() {
        return estadoEndereco;
    }

    public void setEstadoEndereco(String estadoEndereco) {
        this.estadoEndereco = estadoEndereco;
    }

    public String getCidadeEndereco() {
        return cidadeEndereco;
    }

    public void setCidadeEndereco(String cidadeEndereco) {
        this.cidadeEndereco = cidadeEndereco;
    }

    public String getBairroEndereco() {
        return bairroEndereco;
    }

    public void setBairroEndereco(String bairroEndereco) {
        this.bairroEndereco = bairroEndereco;
    }

    public String getRuaEndereco() {
        return ruaEndereco;
    }

    public void setRuaEndereco(String ruaEndereco) {
        this.ruaEndereco = ruaEndereco;
    }

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public String getComplementoEndereco() {
        return complementoEndereco;
    }

    public void setComplementoEndereco(String complementoEndereco) {
        this.complementoEndereco = complementoEndereco;
    }

    public String getCepEndereco() {
        return cepEndereco;
    }

    public void setCepEndereco(String cepEndereco) {
        this.cepEndereco = cepEndereco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPessoa() {
        return pessoa;
    }

    public void setPessoa(Long pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idEndereco);
        hash = 41 * hash + Objects.hashCode(this.estadoEndereco);
        hash = 41 * hash + Objects.hashCode(this.cidadeEndereco);
        hash = 41 * hash + Objects.hashCode(this.bairroEndereco);
        hash = 41 * hash + Objects.hashCode(this.ruaEndereco);
        hash = 41 * hash + Objects.hashCode(this.numeroEndereco);
        hash = 41 * hash + Objects.hashCode(this.complementoEndereco);
        hash = 41 * hash + Objects.hashCode(this.cepEndereco);
        hash = 41 * hash + Objects.hashCode(this.status);
        hash = 41 * hash + Objects.hashCode(this.pessoa);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Endereco other = (Endereco) obj;
        if (!Objects.equals(this.estadoEndereco, other.estadoEndereco)) {
            return false;
        }
        if (!Objects.equals(this.cidadeEndereco, other.cidadeEndereco)) {
            return false;
        }
        if (!Objects.equals(this.bairroEndereco, other.bairroEndereco)) {
            return false;
        }
        if (!Objects.equals(this.ruaEndereco, other.ruaEndereco)) {
            return false;
        }
        if (!Objects.equals(this.numeroEndereco, other.numeroEndereco)) {
            return false;
        }
        if (!Objects.equals(this.complementoEndereco, other.complementoEndereco)) {
            return false;
        }
        if (!Objects.equals(this.cepEndereco, other.cepEndereco)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.idEndereco, other.idEndereco)) {
            return false;
        }
        if (!Objects.equals(this.pessoa, other.pessoa)) {
            return false;
        }
        return true;
    }
    
    
    
}

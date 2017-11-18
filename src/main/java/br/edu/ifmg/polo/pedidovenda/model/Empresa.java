package br.edu.ifmg.polo.pedidovenda.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {
	
	
	@Transient
	private static final long serialVersionUID = 6464971322748837777L;
	
	@Id
	@GeneratedValue
	private Long id;
	private String responsavel;
	private String documentoReceitaFederal;
	private String email;
	
	@NotBlank @Size(max = 150)
	@Column(name = "logradouro", nullable = false, length = 150)
	private String logradouro;
	@NotBlank @Size(max = 20)
	@Column(name = "numero", nullable = false, length = 20)
	private String numero;
	@Size(max = 150)
	@Column(name = "complemento", length = 150)
	private String complemento;
	@NotBlank @Size(max = 60)
	@Column(name = "cidade", nullable = false, length = 60)
	private String cidade;
	@NotBlank @Size(max = 60)
	@Column(name = "uf", nullable = false, length = 60)
	private String uf;
	@NotBlank @Size(max = 9)
	@Column(name = "cep", nullable = false, length = 9)
	private String cep;
	private String latitude;
    private String longitude;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getDocumentoReceitaFederal() {
		return documentoReceitaFederal;
	}
	public void setDocumentoReceitaFederal(String documentoReceitaFederal) {
		this.documentoReceitaFederal = documentoReceitaFederal;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(nullable = false, length = 80)
	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(nullable = false, length = 80)
	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Empresa))
			return false;
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}

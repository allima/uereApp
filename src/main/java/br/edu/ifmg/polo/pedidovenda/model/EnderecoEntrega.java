package br.edu.ifmg.polo.pedidovenda.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class EnderecoEntrega implements Serializable {

	private static final long serialVersionUID = 1L;

	private String logradouro;
	private String numero;
	private String complemento;
	private String cidade;
	private String uf;
	private String cep;
	private String latitude;
	private String longitude;


	@NotBlank @Size(max = 150)
	@Column(name = "entrega_logradouro", nullable = false, length = 150)
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@NotBlank @Size(max = 20)
	@Column(name = "entrega_numero", nullable = false, length = 20)
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Size(max = 150)
	@Column(name = "entrega_complemento", length = 150)
	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@NotBlank @Size(max = 60)
	@Column(name = "entrega_cidade", nullable = false, length = 60)
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@NotBlank @Size(max = 60)
	@Column(name = "entrega_uf", nullable = false, length = 60)
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@NotBlank @Size(max = 9)
	@Column(name = "entrega_cep", nullable = false, length = 9)
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	@Column(name = "entrega_longitude", nullable = false, length = 80)
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Column(name = "entrega_latitude", nullable = false, length = 80)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	

}
